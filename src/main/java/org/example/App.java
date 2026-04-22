package org.example;

import org.example.config.AppConfig;
import org.example.service.BarService;
import org.example.service.HeavyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Minimal Spring IoC/DI practice app.
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("ApplicationContext started");

        BarService barService = context.getBean(BarService.class);
        barService.print();

        System.out.println("Requesting lazy bean now...");
        HeavyService heavyService = context.getBean(HeavyService.class);
        System.out.println(heavyService.work());

        context.close();
    }
}
