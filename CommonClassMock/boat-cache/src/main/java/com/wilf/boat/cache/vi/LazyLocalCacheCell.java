package com.wilf.boat.cache.vi;

import com.wilf.boat.cache.IDataCache;

/**
 * @author wfhstart
 * @create 2024-12-25
 */
public class LazyLocalCacheCell extends CacheCellData implements CacheCell{
    public LazyLocalCacheCell(String cacheName, IDataCache dataCache) {
        super(cacheName, dataCache);
    }

    @Override
    public boolean refresh() {
        super.getDataCache().refreshCache();
        return Boolean.TRUE;
    }
}
