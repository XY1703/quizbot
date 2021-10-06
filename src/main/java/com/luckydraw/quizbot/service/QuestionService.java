package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService extends AbstractService<Question>{
    //TODO move to properties
    public static final String URL = "http://localhost:8080/api/questions/theme/";

    public QuestionService() {
        url = URL;
        clazz = Question.class;
    }

    @Override
    public List<Question> getAll(String param) {
        return super.getAll(URL + param);
    }
}
