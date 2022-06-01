package com.isaolmez.beanswapper.classbased.core;

import lombok.Data;
import org.springframework.aop.target.HotSwappableTargetSource;

@Data
public class TargetSourceDefinition {
    private final HotSwappableTargetSource targetSource;
    private final BeanStubDefinition beanStubDefinition;
    private final Object primaryBean;
    private final Object stubBean;
}
