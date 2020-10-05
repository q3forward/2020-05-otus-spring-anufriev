package ru.otus.homework.production;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Program;
import ru.otus.homework.domain.Product;

@Service
public class BuildService {
    public Product build(Program program) {
        System.out.println("Сборка продукта");
        return new Product(program, "License");
    }
}

