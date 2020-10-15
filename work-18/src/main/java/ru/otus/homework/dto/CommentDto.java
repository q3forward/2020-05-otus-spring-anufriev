package ru.otus.homework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long bookId;
    private String text;
}
