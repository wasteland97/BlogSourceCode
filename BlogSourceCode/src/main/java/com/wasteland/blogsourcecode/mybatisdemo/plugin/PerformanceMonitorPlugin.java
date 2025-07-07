package com.wasteland.blogsourcecode.mybatisdemo.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PerformanceMonitorPlugin implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitorPlugin.class);

    private static final String dataFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    // 慢查询阈值(毫秒)
    private long slowQueryThreshold = 1000;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取执行SQL的相关信息
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql();
        long startTime = System.currentTimeMillis();

        try {
            // 执行原方法
            return invocation.proceed();
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            // 记录日志
            if (costTime > slowQueryThreshold) {
                logger.warn("慢SQL执行耗时: {}ms > {}ms, SQL ID: {}, SQL: {}",
                        costTime, slowQueryThreshold, sqlId, sql);
            } else {
                logger.debug("SQL执行耗时: {}ms, SQL ID: {}, SQL: {}", costTime, sqlId, sql);
            }
            // 可以在这里将统计信息存入数据库或监控系统
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以从配置中读取慢查询阈值
        String threshold = properties.getProperty("slowQueryThreshold");
        if (threshold != null) {
            this.slowQueryThreshold = Long.parseLong(threshold);
        }
    }
}
