package com.isaolmez.beanswapper.classbased.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Component
@ConfigurationProperties("app")
public class BeanStubDefinitions {
    private List<BeanStubDefinition> beanStubDefinitions;
    private Map<String, BeanStubDefinition> beanDefinitionMap;

    @PostConstruct
    public void initialize() {
        beanDefinitionMap = initializeDefinitionMap(beanStubDefinitions);
    }

    private Map<String, BeanStubDefinition> initializeDefinitionMap(List<BeanStubDefinition> beanStubDefinitions) {
        return beanStubDefinitions.stream()
                .collect(Collectors.toMap(BeanStubDefinition::getPrimaryBeanName, Function.identity()));
    }

    public boolean contains(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    public BeanStubDefinition get(String beanName) {
        return beanDefinitionMap.get(beanName);
    }
}
