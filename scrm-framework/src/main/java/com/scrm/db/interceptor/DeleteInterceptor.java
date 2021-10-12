package com.scrm.db.interceptor;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @author liuKevin
 * @date 2021年10月09日 11:28
 */
@Slf4j
@Component
public class DeleteInterceptor extends JsqlParserSupport implements InnerInterceptor {

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler handler = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = handler.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            if (InterceptorIgnoreHelper.willIgnoreBlockAttack(ms.getId())) return;
            BoundSql boundSql = handler.boundSql();
            PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
            mpBs.sql(parserMulti(boundSql.getSql(), null));
        }
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        // 找到对应的table
        String tableName = update.getTable().getName();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
        // 获取del_flag
        String logicColumn = tableInfo.getLogicDeleteFieldInfo().getColumn();
        // 判断是否修改了del_flag
        boolean exists = update.getColumns().stream().anyMatch(k -> k.getColumnName().equals(logicColumn));
        if (exists) {
            // 多改一个del_timestamp
            update.addColumns(new Column("delete_timestamp"));
            update.addExpressions(new StringValue(String.valueOf(System.currentTimeMillis())));
        }
    }
}
