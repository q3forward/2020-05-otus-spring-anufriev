package ru.otus.homework.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResourceLoader {
    private final String resourcePath;

    public ResourceLoader(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * Получение данных из CSV-файла. Читает файл по <b>resourcePath</b> и возвращает строковые данные
     * @return Лист строк с данными
     */
    public List<String> getData() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        List<String> rawDataList = new ArrayList<>();
        try(Scanner scanner = new Scanner(inputStream)){
            while (scanner.hasNextLine()) {
                rawDataList.add(scanner.nextLine());
            }
        }
        return rawDataList;
    }

}
