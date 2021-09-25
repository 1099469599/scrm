/**
 * Copyright(C) 2021 Fugle Technology Co., Ltd. All rights reserved.
 */
package com.scrm.generator.service;

import com.scrm.generator.constants.GenConstants;
import com.scrm.generator.dto.GeneratorDTO;
import com.scrm.generator.mapper.GeneratorMapper;
import com.scrm.generator.utils.GenUtils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * @author LiuZhengyang
 * @since 2021年09月25日 23:06
 */
@Service
@Slf4j
public class GeneratorService {


    @Autowired
    private GeneratorMapper generatorMapper;

    /**
     * 自动根据table创建对应的
     * pojo
     * mapper
     * manager
     * service
     *
     * @param path 生成的路径地址
     * @param tableNames 表名
     */
    public void generatorCode(String path, String... tableNames) {
        // 根据表名首先获取表的
        List<GeneratorDTO> comment = generatorMapper.getTables(CollectionUtil.newArrayList(tableNames));
        // 遍历comment去获取到表的列
        comment.forEach(k -> {
            List<GeneratorDTO> columnList = generatorMapper.getColumn(k.getTableName());
            // 初始化列, 将sql列转化为Java列
            GenUtils.initColumnField(columnList);
            // 初始化Velocity
            initVelocity();
            // 将表转化为context
            VelocityContext velocityContext = prepareContext(k, columnList);
            // 获取模版
            List<String> templateList = getTemplateList();
            //
            for (String template : templateList) {
                if (!StrUtil.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm")) {
                    // 渲染模板
                    StringWriter sw = new StringWriter();
                    Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
                    tpl.merge(velocityContext, sw);
                    FileUtil.writeString(sw.toString(), new File(getPath(k, template, path)), CharsetUtil.UTF_8);
                }
            }
        });
    }


    private String getPath(GeneratorDTO table, String template, String path) {
        return path + File.separator + getFileName(template, table);
    }


    /**
     * 获取文件名
     */
    private String getFileName(String template, GeneratorDTO table) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = "com.scrm";
        // 模块名
        String moduleName = "scrm-service";
        // 大写类名
        String className = StrUtil.upperFirst(StrUtil.toCamelCase(table.getTableName()));
        // 业务名称
        String businessName = table.getTableComment();

        String javaPath = "main/java" + "/" + StrUtil.replace(packageName, ".", "/");
        String mybatisPath = "main/resources/mapper" + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = StrUtil.format("{}/wecom/domain/{}.java", javaPath, className);
        } else if (template.contains("mapper.java.vm")) {
            fileName = StrUtil.format("{}/wecom/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("manager.java.vm")) {
            fileName = StrUtil.format("{}/wecom/manager/{}Manager.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StrUtil.format("{}/wecom/service/I{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StrUtil.format("{}/wecom/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StrUtil.format("{}/web/controller/wecom/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StrUtil.format("{}/{}Mapper.xml", mybatisPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains("api.js.vm")) {
            fileName = StrUtil.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains("index-tree.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }


    /**
     * 初始化vm方法
     */
    private void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, CharsetUtil.UTF_8);
            p.setProperty(Velocity.OUTPUT_ENCODING, CharsetUtil.UTF_8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private VelocityContext prepareContext(GeneratorDTO table, List<GeneratorDTO> columns) {
        // 模块名称
        String moduleName = "scrm-service";
        // 包名
        String packageName = "com.scrm";
        // 类型
        String tplCategory = "crud";
        // 业务名
        String businessName = GenUtils.getBusinessName(table.getTableName());
        // 文件名
        String className = StrUtil.toCamelCase(table.getTableName());
        // 功能
        String functionName = table.getTableComment();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", tplCategory);
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("functionName", StrUtil.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", StrUtil.upperFirst(className));
        velocityContext.put("className", StrUtil.lowerFirst(className));
        velocityContext.put("moduleName", moduleName);
        velocityContext.put("BusinessName", StrUtil.upperFirst(businessName));
        velocityContext.put("businessName", businessName);
        velocityContext.put("basePackage", GenUtils.getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", "S-CRM");
        velocityContext.put("datetime", DateUtil.date());
        velocityContext.put("pkColumn", columns.stream().filter(k -> k.getIsPk() == 1).findFirst().orElseThrow(() -> new RuntimeException("当前表不存在主键")));
        velocityContext.put("importList", getImportList(columns));
        velocityContext.put("columns", columns);
        velocityContext.put("table", table);
        return velocityContext;
    }

    /**
     * 根据列的类型获取导入的包
     *
     * @param columns 列集合
     * @return 返回需要导入的包列表
     */
    private HashSet<String> getImportList(List<GeneratorDTO> columns) {
        HashSet<String> importList = new HashSet<>();
        columns.forEach(k -> {
            if (!GenUtils.checkSuperColumn(k.getJavaField()) && GenConstants.TYPE_DATE.equals(k.getJavaType())) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!GenUtils.checkSuperColumn(k.getJavaField()) && GenConstants.TYPE_BIGDECIMAL.equals(k.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        });
        return importList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    private List<String> getTemplateList() {
        List<String> templates = new ArrayList<>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/manager.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        templates.add("vm/sql/sql.vm");
        templates.add("vm/js/api.js.vm");
        templates.add("vm/vue/index.vue.vm");
        return templates;
    }

}