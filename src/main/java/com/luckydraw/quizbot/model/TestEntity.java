package com.luckydraw.quizbot.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestEntity {

    private Question question;

    private List<Answer> answers;

    public TestEntity(Question question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }
}
