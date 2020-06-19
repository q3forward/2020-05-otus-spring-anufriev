package ru.otus.homework.utils;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ResourceLoader {

    /**
     * Получение данных из CSV-файла. Читает файл по <b>resourcePath</b> и возвращает строковые данные
     * @return Лист строк с данными
     */
    public List<String> getData(String resourcePath) {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        List<String> rawDataList = new ArrayList<>();
        if (inputStream!=null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    rawDataList.add(scanner.nextLine());
                }
            }
        }
        return rawDataList;
    }

}
