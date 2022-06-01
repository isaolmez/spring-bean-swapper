package com.isaolmez.beanswapper.classbased.core;

import lombok.Data;

@Data
public class BeanStubDefinition {

    private String primaryBeanName;
    private String stubBeanName;
    private Class<?> proxiedInterface;
}
