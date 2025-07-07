package com.wilf.boat.cache.vi;

import java.util.Map;

/**
 * 这个属于vi框架接口
 * @author wfhstart
 * @create 2024-12-25
 */
public interface CacheCell {
    String id();

    boolean refresh();

    Map<String, Object> getStatus();

    Object getByKey(String arg1);

    Iterable<String> keys();

    int size();
}
