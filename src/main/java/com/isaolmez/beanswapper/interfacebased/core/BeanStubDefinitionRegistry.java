package com.isaolmez.beanswapper.interfacebased.core;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class BeanStubDefinitionRegistry {

    private final List<BeanStubDefinition> beanDefinitions;
    private final Map<String, BeanStubDefinition> beanDefinitionMap;

    public BeanStubDefinitionRegistry(List<BeanStubDefinitionProvider> providers) {
        beanDefinitions = initializeDefinitions(providers);
        beanDefinitionMap = initializeDefinitionMap(beanDefinitions);
    }

    private Map<String, BeanStubDefinition> initializeDefinitionMap(List<BeanStubDefinition> beanStubDefinitions) {
        return beanStubDefinitions.stream()
                .collect(Collectors.toMap(BeanStubDefinition::getPrimaryBeanName, Function.identity()));
    }

    private List<BeanStubDefinition> initializeDefinitions(List<BeanStubDefinitionProvider> providers) {
        return providers.stream()
                .flatMap(provider -> provider.get().stream())
                .collect(Collectors.toList());
    }

    public boolean contains(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    public BeanStubDefinition get(String beanName) {
        return beanDefinitionMap.get(beanName);
    }
}
