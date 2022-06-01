package com.isaolmez.beanswapper.classbased.core;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class ProxyingMergedBeanPostProcessor implements MergedBeanDefinitionPostProcessor {

    private final BeanFactory beanFactory;
    private final BeanStubDefinitions beanStubDefinitions;
    private final TargetSourceRegistry targetSourceRegistry;

    public ProxyingMergedBeanPostProcessor(BeanFactory beanFactory,
                                           BeanStubDefinitions beanStubDefinitions,
                                           TargetSourceRegistry targetSourceRegistry) {
        this.beanFactory = beanFactory;
        this.beanStubDefinitions = beanStubDefinitions;
        this.targetSourceRegistry = targetSourceRegistry;
    }

    @Override
    public Object postProcessAfterInitialization(Object primaryBean, String beanName) throws BeansException {
        if (beanStubDefinitions.contains(beanName)) {
            final BeanStubDefinition beanStubDefinition = beanStubDefinitions.get(beanName);
            final Object stubBean = beanFactory.getBean(beanStubDefinition.getStubBeanName());
            final HotSwappableTargetSource targetSource = new HotSwappableTargetSource(primaryBean);
            TargetSourceDefinition targetSourceDefinition = new TargetSourceDefinition(targetSource, beanStubDefinition, primaryBean, stubBean);
            targetSourceRegistry.put(beanName, targetSourceDefinition);
            return applyProxy(targetSource);
        }

        return primaryBean;
    }

    private Object applyProxy(HotSwappableTargetSource targetSource) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTargetSource(targetSource);
        return proxyFactoryBean.getObject();
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class<?> aClass, String beanName) {
        if (beanStubDefinitions.contains(beanName)) {
            rootBeanDefinition.setPrimary(true);
        }
    }
}
