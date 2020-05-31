package ru.otus.homework.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Question {

    private Integer questionNum;
    private String questionContent;
    private List<Answer> answerList;

    public Question(Integer num, String questionContent, List<Answer> answerList) {
        this.questionNum = num;
        this.questionContent = questionContent;
        this.answerList = new ArrayList<>(answerList);
    }

    public int getQuestionNum() {
        return questionNum;
    }
    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionContent() {
        return questionContent;
    }
    public void setQuestionContent(String content) {
        this.questionContent = content;
    }

    /*public String getAnswerContent() {
        return this.answerContent;
    }
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Override
    public String toString() {
        return String.format("%d. %s\n%s", questionNum, questionContent, answerContent);
    }*/

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = new ArrayList<>(answerList);
    }

    @Override
    public String toString() {
        return String.format("%d. %s\n%s", questionNum, questionContent, answerList.stream().map(Answer::getAnswerContent).collect(Collectors.joining("\t\t")));
    }
}
