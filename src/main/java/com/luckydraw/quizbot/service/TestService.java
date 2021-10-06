package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Answer;
import com.luckydraw.quizbot.model.Question;
import com.luckydraw.quizbot.model.TestEntity;
import com.luckydraw.quizbot.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TestService {

    private ThemeService themeService;
    private QuestionService questionService;
    private AnswerService answerService;


    @Autowired
    public TestService(ThemeService themeService, QuestionService questionService, AnswerService answerService) {
        this.themeService = themeService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    private List<Question> getQuestionsByThemeCommand(String command){
        Theme theme = themeService.convertCommandToTheme(command);
        List<Question> questions = questionService.getAll(String.valueOf(theme.getId()));
        return questions;
    }

    private List<Answer> getAnswerByQuestionId(Integer questionId){
        List<Answer> answers = answerService.getAll(String.valueOf(questionId));
        return answers;
    }



    public List<TestEntity> createTest(String themeCommand){
        List<TestEntity> testEntity = new ArrayList<>();
        List<Question> questions = getQuestionsByThemeCommand(themeCommand);

        for(Question q : questions){
            testEntity.add(new TestEntity(q, getAnswerByQuestionId(q.getId())));
        }
        return testEntity;
    }
}
