package ru.otus.homework.dao;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootConfiguration
@ComponentScan({"ru.otus.homework.dao", "ru.otus.homework.service", "ru.otus.homework.utils"})
public class TestContextConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/test-bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
