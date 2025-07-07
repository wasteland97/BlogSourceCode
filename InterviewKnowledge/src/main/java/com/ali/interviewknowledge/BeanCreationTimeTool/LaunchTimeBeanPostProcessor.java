package com.ali.interviewknowledge.BeanCreationTimeTool;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author wfhstart
 * @create 2025-02-17
 */
@Component
public class LaunchTimeBeanPostProcessor implements BeanPostProcessor, InstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor {

    @Autowired
    private LaunchTimeManager launchTimeManager;
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        launchTimeManager.beanStart(beanName, System.currentTimeMillis());
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        launchTimeManager.beanEnd(beanName, System.currentTimeMillis());
        return null;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class<?> aClass, String s) {

    }
}
