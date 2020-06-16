package ru.otus.homework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.homework.config.ApplicationSettings;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Localizer {

    private static MessageSource messageSource;
    private static ApplicationSettings appSettings;

    public Localizer(ApplicationSettings appSettings, MessageSource messageSource) {
        this.appSettings = appSettings;
        this.messageSource = messageSource;
    }

    public static List<String> localize(List<String> strs) {
        return strs.stream().map(str -> messageSource.getMessage(str, null, appSettings.getLocale())).collect(Collectors.toList());
    }

    public static String localize(String str) {
        return str!=null ? messageSource.getMessage(str, null, appSettings.getLocale()) : null;
    }

    public static String localize(String str, Object[] values) {
        return str!=null ? messageSource.getMessage(str, values, appSettings.getLocale()) : null;
    }
}
