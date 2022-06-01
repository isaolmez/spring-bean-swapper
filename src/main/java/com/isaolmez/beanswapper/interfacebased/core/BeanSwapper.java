package com.isaolmez.beanswapper.interfacebased.core;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BeanSwapper {

    private final TargetSourceRegistry targetSourceRegistry;

    public BeanSwapper(TargetSourceRegistry targetSourceRegistry) {
        this.targetSourceRegistry = targetSourceRegistry;
    }

    public void swapToInitial(Class<?> clazz){
        Optional<TargetSourceDefinition> targetSourceDetailsOptional = targetSourceRegistry.get(clazz);
        if(targetSourceDetailsOptional.isEmpty()){
            return;
        }

        TargetSourceDefinition targetSourceDefinition = targetSourceDetailsOptional.get();
        targetSourceDefinition.getTargetSource().swap(targetSourceDefinition.getPrimaryBean());
    }

    public void swapToStub(Class<?> clazz){
        Optional<TargetSourceDefinition> targetSourceDetailsOptional = targetSourceRegistry.get(clazz);
        if(targetSourceDetailsOptional.isEmpty()){
            return;
        }

        TargetSourceDefinition targetSourceDefinition = targetSourceDetailsOptional.get();
        targetSourceDefinition.getTargetSource().swap(targetSourceDefinition.getStubBean());
    }
}
