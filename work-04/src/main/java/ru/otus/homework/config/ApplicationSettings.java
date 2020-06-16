package ru.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Locale;

@ConfigurationProperties
public class ApplicationSettings {

    private String csvName;
    private int rightAnswerNeedForPass;
    private Locale locale;

    public String getCsvName() {
        return csvName;
    }

    public void setCsvName(String csvName) {
        this.csvName = csvName;
    }

    public int getRightAnswerNeedForPass() {
        return rightAnswerNeedForPass;
    }

    public void setRightAnswerNeedForPass(int rightAnswerNeedForPass) {
        this.rightAnswerNeedForPass = rightAnswerNeedForPass;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
