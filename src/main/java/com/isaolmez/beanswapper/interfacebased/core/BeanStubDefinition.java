package com.isaolmez.beanswapper.interfacebased.core;

import lombok.Value;

@Value
public class BeanStubDefinition {

    private final String primaryBeanName;
    private final String stubBeanName;
    private final Class<?> proxiedInterface;
}
