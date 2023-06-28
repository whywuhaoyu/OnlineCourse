package com.why.boot.service.impl;

import com.why.boot.bean.SysCourse;
import com.why.boot.bean.SysUserHistory;
import com.why.boot.service.SysCourseService;
import com.why.boot.service.SysUserHistoryService;
import com.why.boot.utils.UserVector;
import com.why.boot.service.TFIDFService;
import com.why.boot.utils.TfIdfDocument;
import com.why.boot.utils.TfIdfVectorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: TFIDFServiceImpl
 * @CreateTime: 2023/4/21 20:35
 */

@Service
public class TFIDFServiceImpl implements TFIDFService {

    private static final int RECOMMENDATION_COUNT = 8; // 自定义最大课程推荐数量

    @Autowired
    SysCourseService sysCourseService;

    @Autowired
    SysUserHistoryService sysUserHistoryService;

    @Override
    public List<SysCourse> TFIDFRecommend(Long userId) {

        // 读取所有课程列表，并将每个课程的标题、描述和关键词提取出来
        List<SysCourse> sysCourseList=sysCourseService.list();
        Map<Long, TfIdfDocument> sysCourseDocs = new HashMap<>();
        for(SysCourse sysCourse:sysCourseList){
            TfIdfDocument doc=new TfIdfDocument();
            doc.addText(sysCourse.getCourseName());
            doc.addText(sysCourse.getCourseDescription());
            doc.addWord(sysCourse.getFirstLabel());
            doc.addWord(sysCourse.getSecondLabel());
            doc.addWord(sysCourse.getThirdLabel());
            sysCourseDocs.put(sysCourse.getCourseId(),doc);
        }

        // 使用分词技术，将所有课程的标题、描述和关键词切分成单词，构建词库和 IDF 值
        TfIdfVectorizer vectorized = new TfIdfVectorizer();
        for (TfIdfDocument doc : sysCourseDocs.values()) {
            vectorized.addDocument(doc);
        }
        vectorized.setMinDf(3);
        vectorized.fit();

        // 计算每个词的 TF-IDF 值
        Map<String, Double> wordIdfMap = new HashMap<>();
        for (String word : vectorized.getVocabulary()) {
            wordIdfMap.put(word, vectorized.getIdf(word));
        }


        // 对于每个用户，计算用户的偏好向量
        Map<Long, UserVector> userVectors = new HashMap<>();
        List<SysUserHistory> histories=sysUserHistoryService.getUserHistoryByUserId(userId);
        for(SysUserHistory history:histories){
            TfIdfDocument doc=sysCourseDocs.get(history.getCourseId());
            if(doc == null){
                continue;
            }
            /* Java 8 中的 Map 接口新增的一种方法 computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)，
            该方法的作用是如果指定键(key)对应的值不存在，则使用提供的映射函数(mappingFunction)计算一个值，创建一个新的 UserVector 对象，
            并将键(key)与该值关联。最终，该方法会返回指定键(key)对应的值。*/
            UserVector userVector=userVectors.computeIfAbsent(userId,k ->new UserVector());
            Set<String> words=doc.getWordSet();   //获取关键词集合
            for(String word:words){
               Double idf=wordIdfMap.get(word);
                if (idf == null) {
                    continue;
                }

                //当前word 的 IDF 值，并乘上当前 history 对象在该关键词上的得分，从而计算得到该关键词在当前文档中的权重
                userVector.add(history.getCourseId(), (float)(idf * history.getScore(word)));
            }
        }

        // 计算用户的偏好向量
        UserVector userVector=userVectors.get(userId);
        if (userVectors.containsKey(userId)) {
            userVector.normalize();          //进行归一化操作，即将向量中的所有值都除以向量的长度，使得向量的长度为 1。
        } else {
            return null;
        }

        // 计算用户和每个课程之间的相似度

        //用于存储所有课程与当前用户之间的相似度。其中，键为 Long 类型的 courseId，值为 Float 类型的相似度值。
        Map<Long, Float> courseSimilarities = new HashMap<>();
        for (Long courseId : sysCourseDocs.keySet()) {
            TfIdfDocument doc = sysCourseDocs.get(courseId);  //根据课程id，获取对应文档
            Set<String> words = doc.getWordSet();             //获取文档中的关键词集合

            UserVector courseVector = new UserVector();      //表示当前课程的向量。
            for (String word : words) {
                Double idf = wordIdfMap.get(word);
                if (idf == null) {
                    continue;
                }
                courseVector.add(courseId, Float.parseFloat(idf.toString()));  //计算对应向量的权重
            }
            courseVector.normalize();  //进行归一化操作

            // 计算余弦相似度
            float similarity = userVector.cosineSimilarity(courseVector); //计算当前用户向量与当前课程向量之间的余弦相似度，并将其存储在 similarity 变量中。
            courseSimilarities.put(courseId, similarity);   //当前课程 courseId 与当前用户之间的相似度 similarity 存储在 courseSimilarities 中
        }

        // 对相似度进行排序，并推荐排名前n的课程
        List<Long> recommendedCourseIds = new ArrayList<>();

        //将 courseSimilarities 转化为包含 Map.Entry 对象的 ArrayList
        List<Map.Entry<Long, Float>> sortedSimilaritied = new ArrayList<>(courseSimilarities.entrySet());

        //对 sortedSimilaritied 列表进行排序,按照 Map.Entry 对象的值（即课程相似度）从高到低排序。
        sortedSimilaritied.sort((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()));
        List<Map.Entry<Long, Float>> sortedSimilarities=new ArrayList<>();
        for (Map.Entry<Long, Float> entry : sortedSimilaritied) {
            if(!entry.getValue().isNaN()){
                //遍历排好序的 sortedSimilaritied 列表，将不为 NaN 的课程相似度存储到 sortedSimilarities 列表中。
                sortedSimilarities.add(entry);
            }
        }

        for (Map.Entry<Long, Float> entry : sortedSimilarities) {
            Long courseId = entry.getKey();
            if (histories.stream().anyMatch(h -> h.getCourseId().equals(courseId))) {   //判断课程id是否在用户历史中出现过
                recommendedCourseIds.add(courseId);       //加入推荐课程id列表
            }

            //如果 recommendedCourseIds 列表中的元素数量达到了要求的 RECOMMENDATION_COUNT，则结束遍历。
            if (recommendedCourseIds.size() >= RECOMMENDATION_COUNT) {
                break;
            }
        }
        // 获取推荐课程列表
        return sysCourseService.getSysCourseByIdListByCount(recommendedCourseIds);
    }
}