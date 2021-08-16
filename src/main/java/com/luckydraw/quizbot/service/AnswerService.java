package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService extends AbstractService<Answer> {

    public static final String URL = "http://localhost:8080/api/answers/question/";

    public AnswerService() {
        url = URL;
        clazz = Answer.class;
    }

    @Override
    public List<Answer> getAll(String param) {
        return super.getAll(URL + param);
    }
}
