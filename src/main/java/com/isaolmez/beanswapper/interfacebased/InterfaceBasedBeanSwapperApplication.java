package com.isaolmez.beanswapper.interfacebased;

import com.isaolmez.beanswapper.interfacebased.core.BeanStubDefinition;
import com.isaolmez.beanswapper.interfacebased.core.BeanStubDefinitionProvider;
import com.isaolmez.beanswapper.interfacebased.core.BeanSwapper;
import com.isaolmez.beanswapper.interfacebased.service.GreetService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InterfaceBasedBeanSwapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceBasedBeanSwapperApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(GreetService greetService, BeanSwapper beanSwapper) {
        return args -> {
            greetService.greet("isa");
            beanSwapper.swapToStub(GreetService.class);
            greetService.greet("isa");
            beanSwapper.swapToInitial(GreetService.class);
            greetService.greet("isa");
        };
    }

    @Bean
    public BeanStubDefinitionProvider myProvider() {
        return () -> List.of(new BeanStubDefinition("englishGreetService", "turkishGreetService", GreetService.class));
    }
}
