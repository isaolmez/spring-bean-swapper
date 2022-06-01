package com.isaolmez.beanswapper.interfacebased.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TargetSourceRegistry {
    private final Map<String, TargetSourceDefinition> targetSourceMap = new HashMap<>();
    private final Map<Class<?>, TargetSourceDefinition> interfaceToTargetSourceMap = new HashMap<>();

    public void put(String beanName, TargetSourceDefinition targetSource){
        targetSourceMap.put(beanName, targetSource);
        interfaceToTargetSourceMap.put(targetSource.getBeanStubDefinition().getProxiedInterface(), targetSource);
    }

    public Optional<TargetSourceDefinition> get(String beanName){
        return Optional.ofNullable(targetSourceMap.get(beanName));
    }

    public Optional<TargetSourceDefinition> get(Class<?> proxiedInterface){
        return Optional.ofNullable(interfaceToTargetSourceMap.get(proxiedInterface));
    }
}
