package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Question;

public class QuestionService extends AbstractService<Question>{

    public static final String URL = "http://localhost:8080/api/questions/theme/";

    public QuestionService() {
        url = URL;
        clazz = Question.class;
    }
}
