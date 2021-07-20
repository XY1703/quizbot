package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Answer;

public class AnswerService extends AbstractService<Answer> {

    public static final String URL = "http://localhost:8080/api/answers/question/";

    public AnswerService() {
        url = URL;
        clazz = Answer.class;
    }
}
