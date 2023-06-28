package com.why.boot.utils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * @Description:    表示一个文档，可以添加文本和单词，计算对应的 TF-IDF 值。
 * @author: why
 * @ClassName: TfIdfDocument
 * @CreateTime: 2023/4/22 10:13
 */


public class TfIdfDocument {
    private final Map<String, Integer> wordCountMap;   //一个用于记录单词出现次数的Map，键为单词，值为出现次数。
    private int numWords;                             //文档中总的单词个数。

    public TfIdfDocument() {
        wordCountMap = new HashMap<>();
        numWords = 0;
    }

    //向文档中添加文本，统计其中每个单词出现的次数并更新wordCountMap。
    public void addText(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            addWord(word);
        }
    }

    //向文档中添加单词，更新wordCountMap和numWords。
    /**getOrDefault(Object key, V defaultValue) 是 Map 接口中的一个方法，用于获取指定键对应的值。如果该键不存在，就返回指定的默认值。
     * 在这里， wordCountMap.getOrDefault(word, 0) 的作用是获取单词 word 在文档中的出现次数，如果单词 word 没有出现过，则返回默认值 0。
     *然后，将获取到的次数加1，即表示新增一个该单词的出现记录。最后，将得到的新的计数值更新到 wordCountMap 中，实现对单词计数 Map 的更新。
     *整个表达式 wordCountMap.getOrDefault(word, 0) + 1 计算的结果就是单词 word 出现的次数加1，用于更新该单词的计数。*/
    public void addWord(String word) {
        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        numWords++;
    }

    //计算单词的 TF-IDF 值
    public double getTfIdf(String word, TfIdfVectorizer vectorizer) {
        int tf = wordCountMap.getOrDefault(word, 0);
        double idf = vectorizer.getIdf(word);
        return tf * idf;
    }

    //获取文档中出现的单词集合，即wordCountMap的关键词集合
    public Set<String> getWordSet() {
        return wordCountMap.keySet();
    }


    //获取单词在文档中出现的次数
    public int getWordCount(String word) {
        return wordCountMap.getOrDefault(word, 0);
    }

    //获取文档中单词的总个数
    public int getNumWords() {
        return numWords;
    }
}
