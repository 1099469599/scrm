package com.scrm.generator.utils;

import com.scrm.generator.constants.GenConstants;
import com.scrm.generator.dto.GeneratorDTO;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.StrUtil;

/**
 * 代码生成器 工具类
 *
 * @author LiuZhengyang
 * @since 2021年09月25日 23:06
 */
public class GenUtils {

    /**
     * 将列转化为Java属性
     *
     * @param columnList 列
     */
    public static void initColumnField(List<GeneratorDTO> columnList) {
        columnList.forEach(k -> {
            String dataType = getDbType(k.getColumnType());
            // 设置Java字段名
            k.setJavaField(StrUtil.toCamelCase(k.getColumnName()));
            // 设置默认的类型
            k.setJavaType(GenConstants.TYPE_STRING);
            // 具体判断如下
            if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
                // 字符串长度超过500设置为文本域
                k.setJavaType(GenConstants.TYPE_STRING);
            } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
                k.setJavaType(GenConstants.TYPE_DATE);
            } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
                // 如果是浮点型 统一用BigDecimal
                String[] str = StrUtil.split(StrUtil.subBetween(k.getColumnType(), "(", ")"), ",");
                if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                    k.setJavaType(GenConstants.TYPE_BIGDECIMAL);
                }
                // 如果是整形
                else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                    k.setJavaType(GenConstants.TYPE_INTEGER);
                }
                // 长整形
                else {
                    k.setJavaType(GenConstants.TYPE_LONG);
                }
            }
        });
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    private static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    private static String getDbType(String columnType) {
        if (StrUtil.indexOfIgnoreCase(columnType, "(") > 0) {
            return StrUtil.subBefore(columnType, "(", false);
        } else {
            return columnType;
        }
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + 1, nameLength);
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StrUtil.sub(packageName, 0, lastIndex);
    }

    /**
     * 判断是否是 BaseEntity的属性
     *
     * @param filedName 属性名
     * @return 是否(true / false)
     */
    public static boolean checkSuperColumn(String filedName) {
        return StrUtil.equalsAnyIgnoreCase(filedName,
                "createBy", "createTime", "updateBy", "updateTime", "id", "corpId", "delFlag", "deleteTimestamp");
    }
}