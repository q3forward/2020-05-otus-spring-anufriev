package ru.otus.homework.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class ResourceLoader")
public class ResourceLoaderTest {

    @DisplayName("test loading CSV")
    @Test
    void loadCSVTest() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.csv");
        List<String> records = new ArrayList<>();
        try(Scanner scanner = new Scanner(inputStream)){
            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
        }

        assertEquals(2, records.size());
        assertEquals("1_QuestionContent", records.get(0));
        assertEquals("2_QuestionContent2_AnswerContent21_AnswerContent22", records.get(1));
    }
}
