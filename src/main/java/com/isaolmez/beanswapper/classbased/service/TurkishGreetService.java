package com.isaolmez.beanswapper.classbased.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TurkishGreetService extends EnglishGreetService {

    @Override
    public void greet(String name) {
        log.info("Merhaba {}!", name);
    }
}
