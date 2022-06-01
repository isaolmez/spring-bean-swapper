package com.isaolmez.beanswapper.interfacebased.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnglishGreetService implements GreetService {

    @Override
    public void greet(String name) {
        log.info("Hello {}!", name);
    }
}
