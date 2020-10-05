package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import ru.otus.homework.domain.Requirement;
import ru.otus.homework.domain.Product;
import ru.otus.homework.integration.SoftwareProduction;

import java.util.Arrays;
import java.util.Collection;

@IntegrationComponentScan
@ComponentScan
@EnableIntegration
public class App {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        SoftwareProduction software = ctx.getBean(SoftwareProduction.class);

        Collection<Requirement> requirements = Arrays.asList(
                new Requirement("бизнес-требования"),
                new Requirement("технические требования"),
                new Requirement("нагрузочные требования"));

        Product product = software.produce(requirements);
        System.out.println("Держите ваш продукт: "+product);
    }

}
