package com.scrm.db.interceptor;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.scrm.annotation.DataScope;
import com.scrm.context.BaseContextHandler;
import com.scrm.dto.user.UserInfo;
import com.scrm.entity.constants.Constant;
import com.scrm.utils.DataScopeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
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

import java.util.List;
import java.util.Objects;
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
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
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
        // 拿到当前线程内部的userId
        final String userId = BaseContextHandler.getUserID();
        if (StrUtil.isBlank(userId)) {
            return;
        }
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        // 处理user_id
        authHandler(userId, ms, plainSelect);
    }

    /**
     * 鉴权处理
     *
     * @param userId      用户Id
     * @param ms          ms
     * @param plainSelect plainSelect
     */
    private void authHandler(String userId, MappedStatement ms, PlainSelect plainSelect) {
        // 获取执行方法的位置
        String namespace = ms.getId();
        // 获取mapper名称
        String className = StrUtil.subBefore(namespace, ".", true);
        // 获取方法名
        String methodName = StrUtil.subAfter(namespace, ".", true);
        log.info("[authHandler] userId:[{}], namespace:[{}], className:[{}], method:[{}] ", userId, namespace, className, methodName);
        //
        Set<DataScope> set = DataScopeUtil.getLocalDataScope();
        Set<String> tableNames = plainSelect.getIntoTables().stream().map(Table::getName).collect(Collectors.toSet());
        DataScope annotation = set.stream().filter(k -> tableNames.contains(k.name())).findFirst().orElse(null);
        if (Objects.nonNull(annotation)) {
            dataPermissionHandler(userId, annotation, plainSelect);
            DataScopeUtil.close();
        }
    }

    /**
     * 数据权限处理
     *
     * @param userId      用户id
     * @param dataScope   annotation
     * @param plainSelect sql
     */
    private void dataPermissionHandler(String userId, DataScope dataScope, PlainSelect plainSelect) {
        // 获取当前用户权限下的用户Id集合, 为了避免收到PageHelp的影响, 所以此处避免查询, 直接获取已经存在的数据
        SaSession session = StpUtil.getSession();
        UserInfo userInfo = (UserInfo) session.get(Constant.SESSION_USER_KEY);
        List<Long> permissionUserIdList = userInfo.getPermissionUserId();
        if (CollectionUtil.isEmpty(permissionUserIdList)) {
            log.warn("[dataPermissionHandler] 当前用户不存在下属员工, 可能是没有登陆, 也可能是超管, userId:[{}]", userId);
            return;
        }
        //
        ItemsList itemsList = new ExpressionList(permissionUserIdList.stream().map(k -> new StringValue(String.valueOf(k))).collect(Collectors.toList()));
        String column = getAliasColumn(dataScope);
        InExpression inExpression = new InExpression(new Column(column), itemsList);
        if (null == plainSelect.getWhere()) {
            // 不存在 where 条件
            plainSelect.setWhere(new Parenthesis(inExpression));
        } else {
            // 存在 where 条件 and 处理
            plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), inExpression));
        }
    }

    /**
     * 获取字段名称
     *
     * @param dataScope annotation
     * @return 字段名
     */
    private String getAliasColumn(DataScope dataScope) {
        String column = dataScope.name();
        if (StrUtil.isNotBlank(dataScope.alias())) {
            column = StrUtil.builder().append(dataScope.alias()).append(StringPool.DOT).append(dataScope.name()).toString();
        }
        return column;
    }

}
