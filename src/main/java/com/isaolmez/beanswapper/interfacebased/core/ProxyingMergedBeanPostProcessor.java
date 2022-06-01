package com.isaolmez.beanswapper.interfacebased.core;

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
    private final BeanStubDefinitionRegistry beanStubDefinitionRegistry;
    private final TargetSourceRegistry targetSourceRegistry;

    public ProxyingMergedBeanPostProcessor(BeanFactory beanFactory,
                                           BeanStubDefinitionRegistry beanStubDefinitionRegistry,
                                           TargetSourceRegistry targetSourceRegistry) {
        this.beanFactory = beanFactory;
        this.beanStubDefinitionRegistry = beanStubDefinitionRegistry;
        this.targetSourceRegistry = targetSourceRegistry;
    }

    @Override
    public Object postProcessAfterInitialization(Object primaryBean, String beanName) throws BeansException {
        if (beanStubDefinitionRegistry.contains(beanName)) {
            final BeanStubDefinition beanStubDefinition = beanStubDefinitionRegistry.get(beanName);
            final Object stubBean = beanFactory.getBean(beanStubDefinition.getStubBeanName());
            final HotSwappableTargetSource targetSource = new HotSwappableTargetSource(primaryBean);
            targetSourceRegistry.put(beanName, new TargetSourceDefinition(targetSource, beanStubDefinition, primaryBean, stubBean));
            return applyProxy(targetSource, beanStubDefinition.getProxiedInterface());
        }

        return primaryBean;
    }

    private Object applyProxy(HotSwappableTargetSource targetSource, Class<?> proxiedInterface) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTargetSource(targetSource);
        proxyFactoryBean.addInterface(proxiedInterface);
        return proxyFactoryBean.getObject();
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class<?> aClass, String beanName) {
        if (beanStubDefinitionRegistry.contains(beanName)) {
            rootBeanDefinition.setPrimary(true);
        }
    }
}
