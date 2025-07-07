package com.wasteland.blogsourcecode.jconsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wfhstart
 * @create 2025-03-15
 */
public class OOMDemo {

    private static final Map<Integer, List<Integer>> map = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            map.put(1, new ArrayList<>(i));
            Thread.sleep(1000);
        }
    }
}
