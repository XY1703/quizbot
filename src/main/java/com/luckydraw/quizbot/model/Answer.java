package com.luckydraw.quizbot.model;

import lombok.Data;

@Data
public class Answer {

    private Integer id;

    private String text;

    private Integer questionId;
}
