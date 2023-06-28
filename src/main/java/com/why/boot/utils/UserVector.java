package com.why.boot.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: UserVector
 * @CreateTime: 2023/4/22 11:45
 */

public class UserVector {
    private Map<Long, Float> vector = new HashMap<>();

    public UserVector() {}


    /**
     * 实现了向稀疏向量中添加项的功能，以在某些情况下实现向量化。
     * 具体来说，该方法接收两个参数：一个 Long 类型的 itemId 表示向量中的一维坐标，一个 float 类型的 value 表示在该坐标处的向量值。
     * 首先通过 vector.getOrDefault(itemId, 0.0f) 方法获取在 itemId 处的原有值，如果该值未被存储，则默认为 0.0f。
     * 然后，将该位置的向量值更新为原有值加上参数 value，以实现向量化的效果。*/
    public void add(Long itemId, float value) {
        float existingValue = vector.getOrDefault(itemId, 0.0f);
        vector.put(itemId, existingValue + value);
    }

    public Map<Long, Float> getVector() {
        return vector;
    }


    /**
     * 实现了对稀疏向量进行归一化（Normalization）的操作，用于将向量中的每个元素值按比例缩放，满足某些特定的需求。
     * 具体来说，该方法将向量中的所有元素值相加，得到所有元素值的总和，然后按照比例缩放每个元素值，使得它们的和等于 1。
     * 该方法首先遍历整个向量，计算出所有元素值的总和，并将其保存在变量 totalValue 中。
     * 然后，再次遍历整个向量，将每个元素的值除以总和，计算出每个元素在向量中的比例，最后将该比例值设置为当前元素的值。*/
    public void normalize() {
        float totalValue = 0;
        for (Map.Entry<Long, Float> entry : vector.entrySet()) {
            totalValue += entry.getValue();
        }
        for (Map.Entry<Long, Float> entry : vector.entrySet()) {
            float value = entry.getValue() / totalValue;
            entry.setValue(value);
        }
    }

    @Override
    public String toString() {
        return vector.toString();
    }


    /**
     * 实现了稀疏向量之间的点积（dot product）计算，返回两个向量之间的乘积总和。
     * 该方法接收一个 UserVector 类型的参数 other 作为另一个向量，
     * 首先获取当前向量的 Map<Long, Float> 类型的 vector，然后遍历 vector 中的每个元素，将当前向量和参数向量相对应项值相乘，并将乘积加到变量 product 中。
     * */
    public float dotProduct(UserVector other) {
        float product = 0;
        Map<Long, Float> thisMap = this.vector;
        Map<Long, Float> otherMap = other.getVector();
        for (Long itemId : thisMap.keySet()) {
            Float value1 = thisMap.get(itemId);
            Float value2 = otherMap.get(itemId);
            if (value1 != null && value2 != null) {
                product += value1 * value2;
            }
        }
        return product;
    }


    /**
     * 计算稀疏向量之间的余弦相似度（cosine similarity），返回两个向量之间夹角的余弦值。*/
    public float cosineSimilarity(UserVector other) {
        float dotProduct = dotProduct(other);
        float norm1 = (float) Math.sqrt(dotProduct(this));
        float norm2 = (float) Math.sqrt(dotProduct(other));
        return dotProduct / (norm1 * norm2);
    }
}

