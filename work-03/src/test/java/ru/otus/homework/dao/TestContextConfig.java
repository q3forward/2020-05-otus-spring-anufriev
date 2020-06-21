package ru.otus.homework.dao;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootConfiguration
@ComponentScan({"ru.otus.homework.dao"})
public class TestContextConfig {

}
