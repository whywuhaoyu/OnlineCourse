package com.why.boot.utils;
import java.util.*;

/**
 * @Description:     表示词库和 IDF 值的计算器。
 * @author: why
 * @ClassName: TfIdfVectorizer
 * @CreateTime: 2023/4/22 10:14
 */


public class TfIdfVectorizer {
    private int minDf; // 新增最小文档频率参数
    private final Map<String, Integer> documentCountMap;  // 统计每个单词在多少个文档中出现过，这里用一个Map做缓存
    private int numDocuments;  // 总文档数
    private List<String> vocabulary; //词汇表，表示输入文本数据中所有不同单词的集合。

    public TfIdfVectorizer() {
        minDf = 1;
        documentCountMap = new HashMap<>();
        numDocuments = 0;
        vocabulary = new ArrayList<>();
    }

    //minDf可自定义赋值
    public void setMinDf(int minDf) {
        this.minDf = minDf;
    }

    //添加一个文档
    /**
     * 向当前 TF-IDF 向量器中添加一个文档。
     * 该方法会计算每个单词在该文档中的出现次数，并更新 documentCountMap，然后将文档数目 numDocuments 加 1。
     * */
    public void addDocument(TfIdfDocument document) {
        Set<String> words = document.getWordSet();
        for (String word : words) {
            documentCountMap.put(word, documentCountMap.getOrDefault(word, 0) + 1);
        }
        numDocuments++;
    }

    //获取单词的 IDF 值
    /**获取给定单词 word 的 IDF 值。
     * 该方法会根据 documentCountMap 中的记录计算出单词在所有文档中的逆文档频率，即 IDF 值。*/
    public double getIdf(String word) {
        int docCount = documentCountMap.getOrDefault(word, 0);    //表示包含当前单词的文档数目，也就是与输入的单词 word 相关的文档数量
        return Math.log((double) numDocuments / (docCount + 1));             //(docCount + 1) 是为了避免分母为 0 而设置的平滑值。最后，使用 Math.log() 函数对计算结果取自然对数。该方法的返回值是计算得到的 IDF 值。
    }



    // fit方法
    /**
     * 首先遍历 documentCountMap 中所有的键（单词）和对应的值（单词在文档中出现的次数）。
     * 对于每个单词，首先获取其在文档集合中出现的次数，然后判断其是否符合最小文档频率（minDf）的要求，如果小于最小文档频率，则跳过该单词，继续处理下一个单词。
     * 如果该单词的文档频率不小于最小文档频率，则根据 IDF 的公式计算出该单词的 IDF 值，并将其更新回 documentCountMap 中。同时，将该单词添加到词汇表 vocabulary 中。
     * 最后，将词汇表按照字典序进行排序，以便后续进行向量化时的词语顺序一致。*/
    public void fit() {
        for (String word : documentCountMap.keySet()) {
            int docCount = documentCountMap.get(word);
            if (docCount < minDf) {
                continue;
            }
            documentCountMap.put(word, docCount);
            vocabulary.add(word);
        }
        Collections.sort(vocabulary);
    }

    //获取所有单词的 IDF 值
    public Map<String, Double> getIdfMap() {
        Map<String, Double> idfMap = new HashMap<>();
        for (String word : documentCountMap.keySet()) {
            double idf = getIdf(word);
            idfMap.put(word, idf);
        }
        return idfMap;
    }

    //获取词汇列表
    /**Collections.unmodifiableList 是 Java 标准库中提供的一种集合操作方法，用于创建一个不可修改的 List 类型集合。
     * 它接收一个 List 类型的集合作为参数，并返回包装后的 List 类型集合，防止外部代码对集合进行修改。该方法的作用是保护集合的不变性和一致性。
     * */
    public List<String> getVocabulary() {
        return Collections.unmodifiableList(vocabulary);    //返回不可修改的词汇表 vocabulary
    }

    public int getIndexOfTerm(String term) {
        return vocabulary.indexOf(term);
    }
}
