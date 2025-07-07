package com.wilf.boat.cache.vi;

import com.wilf.boat.cache.IDataCache;
import com.wilf.boat.cache.local.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author wfhstart
 * @create 2024-12-25
 */
public class CacheMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheMonitor.class);
    private static final CacheMonitor INSTANCE = new CacheMonitor();

    public static CacheMonitor getInstance() {
        return INSTANCE;
    }

    public void start() {
        startVI();
    }

    private void startVI() {
        try {
            Map<String, IDataCache> cacheAll = CacheManager.getInstance().getAllLocalCache();
            for (Map.Entry<String, IDataCache> entry : cacheAll.entrySet()) {
                // com.ctrip.framework.vi.cacheRefresh.CacheManager.add(new LocalCacheCell(entry.getKey(), entry.getValue()));
            }

            Map<String, IDataCache> lazyCacheAll = CacheManager.getInstance().getAllLazyCache();
            for (Map.Entry<String, IDataCache> entry : lazyCacheAll.entrySet()) {
                // com.ctrip.framework.vi.cacheRefresh.CacheManager.add(new LazyLocalCacheCell(entry.getKey(), entry.getValue()));
            }
        } catch (Exception e) {
            LOGGER.error("start cache vi failed.", e);
        }
    }
}
