package com.luckydraw.quizbot.bot.handler;

import com.luckydraw.quizbot.bot.State;
import com.luckydraw.quizbot.model.TestEntity;
import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.*;
import com.luckydraw.quizbot.util.BotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Component
public class QuizHandler implements Handler{

    private ThemeService themeService;
    private UserService userService;
    private TestService testService;
    private List<TestEntity> test = new ArrayList<>();

    private static final List<String> OPTIONS = List.of("A", "B", "C", "D");
    private static final String ANSWER_CORRECT = "/answer_correct";
    private static final String ANSWER_INCORRECT = "/answer_incorrect";
    private static final String START_QUIZ = "/start_quiz";

    @Autowired
    public QuizHandler(ThemeService themeService, UserService userService, TestService testService) {
        this.themeService = themeService;
        this.userService = userService;
        this.testService = testService;
    }

    @Override
    public State operatedState() {
        return State.PLAYING_QUIZ;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        List<String> list = themeService.getThemesListAsCommand();
        list.add(ANSWER_CORRECT);
        list.add(ANSWER_INCORRECT);
        list.add(START_QUIZ);

        return list;
    }

    @Override
    public List<Object> handle(User user, String message) {
            return message.equals(ANSWER_INCORRECT) || message.equals(ANSWER_CORRECT) ? nextQuestion(user) : startQuiz(user, message);
    }

    private List<Object> startQuiz(User user, String message){
        user.setState(State.PLAYING_QUIZ);
        userService.save(user);
        test = testService.createTest(message);

        return nextQuestion(user);
    }

    private List<Object> nextQuestion(User user){
        if(!test.isEmpty()){
        TestEntity testEntity = test.get(0);
        //TODO Refactor this
        String questionText = String.format("%s \n\n %s \n\n %s \n\n %s\n\n %s",
                                                        testEntity.getQuestion().getText(), testEntity.getAnswers().get(0).getText(),
                                                            testEntity.getAnswers().get(1).getText(),
                                                                testEntity.getAnswers().get(2).getText(),
                                                                    testEntity.getAnswers().get(3).getText());

        SendMessage sm = BotUtil.createMessageTemplate(user);
        sm.setText(questionText);
        sm.setReplyMarkup(createKeyboard(testEntity));

        test.remove(0);

        return List.of(sm);
        } else {
            return returnToThemes(user);
        }
    }

    private List<Object> returnToThemes(User user){
        SendMessage sm = BotUtil.createMessageTemplate(user);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(BotUtil.createInlineKeyboardButton("Выбрать тему", ThemeHandler.SHOW_THEMES));
        inlineKeyboardMarkup.setKeyboard(List.of(row));

        sm.setText("Тест пройден.");
        sm.setReplyMarkup(inlineKeyboardMarkup);

        return List.of(sm);
    }

    private InlineKeyboardMarkup createKeyboard(TestEntity testEntity){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> rowOne = new ArrayList<>();
        List<InlineKeyboardButton> rowTwo = new ArrayList<>();

        for(int i = 0; i < testEntity.getAnswers().size(); i++){

            InlineKeyboardButton button = BotUtil.createInlineKeyboardButton(OPTIONS.get(i),
                    testEntity.getQuestion().getRightAnswerId() == testEntity.getAnswers().get(i).getId() ?
                            ANSWER_CORRECT : ANSWER_INCORRECT);

            if(i < 2) rowOne.add(button);
            else rowTwo.add(button);
        }

        inlineKeyboardMarkup.setKeyboard(List.of(rowOne, rowTwo));

        return inlineKeyboardMarkup;
    }
}
