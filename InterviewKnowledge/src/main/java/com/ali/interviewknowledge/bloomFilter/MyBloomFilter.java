package com.ali.interviewknowledge.bloomFilter;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wfhstart
 * @create 2025-02-14
 */
public class MyBloomFilter {

    // 位数组最小容量
    private static final int MIN_SIZE = 10000;

    // 位数组初始容量
    private int capacity = Integer.MAX_VALUE;

    // hash函数的种子因子
    private static final int[] SEEDS = new int[]{5, 7, 11, 13, 31, 37, 61};

    // 位数组，记录数据是否出现过
    private BitSet bits = null;

    private SimpleHash[] func = new SimpleHash[SEEDS.length];

    // 无参构造
    public MyBloomFilter() {
        // 按照默认大小
        init();
    }

    // 带参构造
    public MyBloomFilter(int size) {
        // 大小初始化最小容量
        if (size >= MIN_SIZE) {
            capacity = size;
        }
        init();
    }

    private void init () {
        // 初始化位数组
        bits = new BitSet(capacity);

        // 初始化hash函数
        for (int i = 0; i < SEEDS.length; i++) {
            func[i] = new SimpleHash(capacity, SEEDS[i]);
        }
    }


    /**
     * 添加元素到位数组
     */
    public void add(Object value) {
        for (SimpleHash f : func) {
            // 添加到bitset
            bits.set(f.hash(value), true);
        }
    }

    /**
     * 判断元素的特征是否存在于位数组
     * @param value
     * @return
     */
    public boolean contains(Object value) {
        boolean result = true;
        for (SimpleHash f : func) {
            // 如果有一个hash值不存在，则返回false
            if (!bits.get(f.hash(value))) {
                result = false;
                return result;
            }
        }
        return result;
    }


    // 静态内部类。用于 hash 操作
    public static class SimpleHash {
        // 位数组大小
        private int cap;

        // hash种子
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        // hash函数
        public int hash(Object value) {
            // 下面方法参照了hashmap里的方法

            if (value == null) {
                return 0;
            } else {
                // hash值
                int hash1 = value.hashCode();
                // 高位的hash值
                int hash2 = hash1 >>> 16;
                // 合并hash值(相当于把高低位的特征结合)
                int combine = hash2 ^ hash1;
                // 相乘再取余
                return Math.abs(combine * seed) & (cap - 1);
            }
        }
    }

    public static void main(String[] args) {
        // 方式一：自实现
        String value1 = "7749";
        String value2 = "8864";
        MyBloomFilter filter = new MyBloomFilter();
        System.out.println(filter.contains(value1));
        System.out.println(filter.contains(value2));
        filter.add(value1);
        filter.add(value2);
        System.out.println(filter.contains(value1));
        System.out.println(filter.contains(value2));

        // 方式二：利用Guava自带的布隆过滤器
//        BloomFilter<String> bloomFilter = BloomFilter.create(
//                Funnels.stringFunnel(Charsets.UTF_8),1000000,0.04);
//
//        bloomFilter.put("Sam");
//
//        System.out.println(bloomFilter.mightContain("Jane"));
//        System.out.println(bloomFilter.mightContain("Sam"));

        // 方式三：利用Redission来创建布隆过滤器
    }
}
