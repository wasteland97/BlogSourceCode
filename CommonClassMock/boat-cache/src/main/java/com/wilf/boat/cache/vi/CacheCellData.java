package com.wilf.boat.cache.vi;

import com.google.common.collect.Lists;
import com.wilf.boat.cache.DataCacheStats;
import com.wilf.boat.cache.IDataCache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wfhstart
 * @create 2024-12-25
 */
@SuppressWarnings("rawtypes")
public class CacheCellData {
    private IDataCache dataCache;

    private String cacheName;

    public CacheCellData(String cacheName, IDataCache dataCache) {
        this.cacheName = cacheName;
        this.dataCache = dataCache;
    }

    public String id() {
        return cacheName;
    }

    public Map<String, Object> getStatus() {
        return getCacheStatsMap(dataCache);
    }

    public boolean refresh() {
        dataCache.refreshCache();
        return Boolean.TRUE;
    }

    public int size() {
        return (int) dataCache.size();
    }

    @SuppressWarnings("unchecked")
    public Object getByKey(String arg0) {
        if (arg0 != null) {
            Object key = arg0.toString();
            if (getKeyType() == Integer.class) {
                key = Integer.parseInt(arg0.toString());
            }
            return dataCache.get(key);
        }
        return null;
    }

    public Iterable keys() {
        List<String> keys = Lists.newArrayList();
        Iterator iterator = dataCache.getAll().keySet().iterator();
        while (iterator.hasNext()) {
            keys.add(iterator.next().toString());
        }
        return keys;
    }

    private Type getKeyType() {
        Type type = dataCache.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types != null && types.length > 0 && types[0] != null) {
                return types[0];
            }
        }
        return String.class;
    }

    public IDataCache getDataCache() {
        return dataCache;
    }

    public String getCacheName() {
        return cacheName;
    }

    private Map<String, Object> getCacheStatsMap(IDataCache cache) {
        Map<String, Object> map = new LinkedHashMap<>();
        DataCacheStats stats = cache.getStats();
        NumberFormat percent = NumberFormat.getPercentInstance();// 建立百分比格式化用
        percent.setMaximumFractionDigits(1); // 百分比小数点后的位数
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("MaximumSize", stats.getMaximumSize());
        map.put("RefreshPeriod", stats.getRefreshPeriod() + " min");
        map.put("Size", cache.size());
        map.put("HitCount", stats.getHitsPerSeconds() + "/s");
        map.put("TotalHits", stats.getTotalHits());
        map.put("CreateTime", df.format(stats.getCreateTime()));
        return map;
    }
}
