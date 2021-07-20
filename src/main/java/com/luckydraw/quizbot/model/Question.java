package com.luckydraw.quizbot.model;

import lombok.Data;

@Data
public class Question {

    private Integer id;

    private String text;

    private String rightAnswerId;
}
