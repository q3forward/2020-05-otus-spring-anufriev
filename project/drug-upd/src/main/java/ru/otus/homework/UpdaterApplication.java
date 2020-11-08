package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EnableCircuitBreaker
public class UpdaterApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UpdaterApplication.class);
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1");
    }

}
