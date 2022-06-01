package com.isaolmez.beanswapper.classbased;

import com.isaolmez.beanswapper.classbased.core.BeanSwapper;
import com.isaolmez.beanswapper.classbased.service.EnglishGreetService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:classbased/application.properties")
public class ClassBasedBeanSwapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassBasedBeanSwapperApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(EnglishGreetService greetService, BeanSwapper beanSwapper) {
        return args -> {
            greetService.greet("isa");
            beanSwapper.swapToStub(EnglishGreetService.class);
            greetService.greet("isa");
            beanSwapper.swapToInitial(EnglishGreetService.class);
            greetService.greet("isa");
        };
    }

//    @Bean
//    public BeanStubDefinitionProvider myProvider() {
//        return () -> List.of(new BeanStubDefinition("englishGreetService", "turkishGreetService", EnglishGreetService.class));
//    }
}
