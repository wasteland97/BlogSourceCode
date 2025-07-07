package com.ali.interviewknowledge.NormalProblem.mapDepthFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wfhstart
 * @create 2025-04-15
 */
public class MapDepthFinderDemo {
    public static int findMaxDepthForKey(Map<String, Object> map, String targetKey) {
        return findMaxDepthForKey(map, targetKey, 1);
    }

    private static int findMaxDepthForKey(Map<String, Object> map, String targetKey, int currentDepth) {
        int maxDepth = 0;

        // 检查当前层是否有目标key
        if (map.containsKey(targetKey)) {
            maxDepth = currentDepth;
        }

        // 递归检查所有嵌套的Map
        for (Object value : map.values()) {
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                int nestedDepth = findMaxDepthForKey(nestedMap, targetKey, currentDepth + 1);
                maxDepth = Math.max(maxDepth, nestedDepth);
            }
        }

        return maxDepth;
    }

    public static void main(String[] args) {
        // 示例Map结构
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map11 = new HashMap<>();
        map11.put("k1", null);
        map1.put("k1", map11);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("k1", null);
        map.put("k2", map1);
        map.put("k3", map2);
        map.put("k4", null);

        int maxDepth = findMaxDepthForKey(map, "k1");
        System.out.println("Key 'k1'的最大深度为: " + maxDepth);
    }
}
