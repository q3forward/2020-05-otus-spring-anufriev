package ru.otus.homework.domain;

public class Answer {

    private String answerContent;

    public Answer(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getAnswerContent() {
        return this.answerContent;
    }
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Override
    public String toString() {
        return answerContent;
    }
}
