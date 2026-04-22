package org.example.config;

import org.example.service.AnotherFooImpl;
import org.example.service.BarService;
import org.example.service.Foo;
import org.example.service.FooImpl;
import org.example.service.HeavyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfig {

    @Bean
    public Foo fooBean() {
        return new FooImpl();
    }

    @Bean
    public Foo anotherFooBean() {
        return new AnotherFooImpl();
    }

    @Bean
    public FooImpl fooImplBean() {
        return new FooImpl();
    }

    @Bean
    public BarService barService(@Qualifier("fooBean") Foo foo) {
        return new BarService(foo);
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    @Lazy
    public HeavyService heavyService() {
        return new HeavyService();
    }
}
