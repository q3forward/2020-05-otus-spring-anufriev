package ru.otus.homework.service;

import java.util.List;

public interface Localizer {

    List<String> localize(List<String> strs);

    String localize(String str);

    String localize(String str, Object... values);
}
