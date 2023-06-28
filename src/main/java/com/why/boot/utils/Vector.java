package com.why.boot.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: Vector
 * @CreateTime: 2023/4/22 11:34
 */

public class Vector {
    private Map<String, Double> elements;

    public Vector() {
        elements = new HashMap<>();
    }


    /*向向量中添加一个特征词及其权重；*/
    public void add(String term, double weight) {
        elements.put(term, weight);
    }

    /*获取向量中指定特征词的权重，如果该特征词不存在，则返回 0；*/
    public double get(String term) {
        return elements.getOrDefault(term, 0.0);
    }

    /*设置向量中指定特征词的权重；*/
    public void set(String term, double weight) {
        elements.put(term, weight);
    }

    /*对向量进行归一化，即将每个特征词的权重除以向量的模长，使得向量的长度为 1；*/
    public void normalize() {
        // 计算向量的模长
        double norm = 0;
        for (Double weight : elements.values()) {
            norm += weight * weight;
        }
        norm = Math.sqrt(norm);

        // 归一化向量
        for (String term : elements.keySet()) {
            double weight = elements.get(term);
            elements.put(term, weight / norm);
        }
    }

    /*方法用于计算当前向量与另一个向量之间的点积，即将两个向量中相同特征词的权重相乘。*/
    public double dotProduct(Vector other) {
        double result = 0;
        for (String term : elements.keySet()) {
            double weight = elements.get(term);
            double otherWeight = other.get(term);
            result += weight * otherWeight;
        }
        return result;
    }
}
