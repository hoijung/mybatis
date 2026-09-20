package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { java.sql.Connection.class, Integer.class }))
public class SqlLoggingInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(SqlLoggingInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");

        log.info("SQL: {}", formatSql(boundSql, configuration));
        return invocation.proceed();
    }

    private String formatSql(BoundSql boundSql, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("\\s+", " ").trim();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> mappings = boundSql.getParameterMappings();

        for (ParameterMapping mapping : mappings) {
            String property = mapping.getProperty();
            Object value = null;

            if (boundSql.hasAdditionalParameter(property)) {
                value = boundSql.getAdditionalParameter(property);
            } else if (parameterObject != null) {
                MetaObject parameterMetaObject = configuration.newMetaObject(parameterObject);
                if (parameterMetaObject.hasGetter(property)) {
                    value = parameterMetaObject.getValue(property);
                } else if (parameterObject instanceof Map<?, ?> parameterMap) {
                    value = parameterMap.get(property);
                } else if (mappings.size() == 1) {
                    value = parameterObject;
                }
            }

            sql = sql.replaceFirst("\\?", java.util.regex.Matcher.quoteReplacement(toSqlLiteral(value)));
        }
        return sql;
    }

    private String toSqlLiteral(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof Date date) {
            return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "'";
        }
        return "'" + value.toString().replace("'", "''") + "'";
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}