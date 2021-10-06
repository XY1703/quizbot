package com.luckydraw.quizbot.bot.handler;


import com.luckydraw.quizbot.bot.State;
import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.UserService;
import com.luckydraw.quizbot.util.BotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Component
public class RegistrationHandler implements Handler{


    private UserService service;

    @Autowired
    public RegistrationHandler(UserService service) {
        this.service = service;
    }

    @Override
    public State operatedState() {
        return State.ENTER_NAME;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        //TODO move string to properties
        return List.of("/enter_name_accept");
    }

    @Override
    public List<Object> handle(User user, String message) {
        user.setState(State.NONE);
        user.setName(message);
        service.save(user);

        SendMessage enteredName = BotUtil.createMessageTemplate(user);
        enteredName.setText("Ваш ник " + message);
        enteredName.enableHtml(true);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> row = List.of(BotUtil.createInlineKeyboardButton("Выбрать тему", ThemeHandler.SHOW_THEMES));
        inlineKeyboardButtons.add(row);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        enteredName.setReplyMarkup(inlineKeyboardMarkup);

        return List.of(enteredName);
    }

}
