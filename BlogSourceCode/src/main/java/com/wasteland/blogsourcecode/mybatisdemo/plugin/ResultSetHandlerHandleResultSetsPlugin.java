package com.wasteland.blogsourcecode.mybatisdemo.plugin;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
@Component
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class ResultSetHandlerHandleResultSetsPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Statement stmt = (Statement) invocation.getArgs()[0];

        // 创建自定义的 EncryptingResultSetHandler
        EncryptingResultSetHandler customResultSetHandler = new EncryptingResultSetHandler((ResultSetHandler) invocation.getTarget());
//        Object result = invocation.proceed();
        // 使用自定义的 EncryptingResultSetHandler 重新处理结果集
        return customResultSetHandler.handleResultSets(stmt);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以为插件配置属性
    }
}
