/**
 * Copyright(C) 2021 Fugle Technology Co., Ltd. All rights reserved.
 */
package com.scrm.generator.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author LiuZhengyang
 * @version $Id$
 * @since 2021年09月25日 23:11
 */
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtils.beanFactory = configurableListableBeanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 根据class类型找到对应的bean对象
     *
     * @param clz 对象class
     *
     * @return bean对象
     */
    public static <T> T getBean(Class<T> clz) {
        return beanFactory.getBean(clz);
    }
}