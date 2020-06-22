package ru.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationSettings;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizerImpl implements Localizer {

    private final MessageSource messageSource;
    private final ApplicationSettings appSettings;

    public LocalizerImpl(ApplicationSettings appSettings, MessageSource messageSource) {
        this.appSettings = appSettings;
        this.messageSource = messageSource;
    }

    @Override
    public List<String> localize(List<String> strs) {
        return strs.stream().map(str -> messageSource.getMessage(str, null, appSettings.getLocale())).collect(Collectors.toList());
    }

    @Override
    public String localize(String str) {
        return str!=null ? messageSource.getMessage(str, null, appSettings.getLocale()) : null;
    }

    @Override
    public String localize(String str, Object... values) {
        return str!=null ? messageSource.getMessage(str, values, appSettings.getLocale()) : null;
    }
}
