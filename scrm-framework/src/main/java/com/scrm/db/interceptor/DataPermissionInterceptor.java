package com.scrm.db.interceptor;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.scrm.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liuKevin
 * @date 2021年10月08日 14:25
 */
@Slf4j
@Component
public class DataPermissionInterceptor extends JsqlParserSupport implements InnerInterceptor {


    /**
     * {@link Executor#query(MappedStatement, Object, RowBounds, ResultHandler, CacheKey, BoundSql)} 操作前置处理
     * <p>
     * 改改sql啥的
     *
     * @param executor      Executor(可能是代理对象)
     * @param ms            MappedStatement
     * @param parameter     parameter
     * @param rowBounds     rowBounds
     * @param resultHandler resultHandler
     * @param boundSql      boundSql
     */
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(parserSingle(mpBs.sql(), ms));
    }

    /**
     * 查询
     *
     * @param select 查询select
     * @param index  当前参数index
     * @param sql    具体执行的SQL
     * @param obj    SQL入参对象
     */
    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        MappedStatement ms = PluginUtils.realTarget(obj);
        // 先判断是不是SELECT操作 不是直接过滤
        if (!SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
            return;
        }
        final String userId = BaseContextHandler.getUserID();
        if (StrUtil.isBlank(userId)) {
            return;
        }
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        authHandler(userId, ms, plainSelect);
    }

    /**
     * 鉴权处理
     *
     * @param userId
     * @param ms
     * @param plainSelect
     */
    private void authHandler(String userId, MappedStatement ms, PlainSelect plainSelect) {
        log.info("userID  {} ", userId);
        //获取执行方法的位置
        String namespace = ms.getId();
        log.info("namespace  {} ", namespace);
        //获取mapper名称
        String className = StrUtil.subBefore(namespace, ".", true);
        log.info("className  {} ", className);
        //获取方法名
        String methodName = StrUtil.subAfter(namespace, ".", true);
        log.info("methodName  {} ", methodName);
        try {
            final Method[] methods = Class.forName(className).getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    // TODO 处理数据权限
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("找不到文件 {}", e.toString());
        }
    }

}
