package com.luckydraw.quizbot.bot;


import com.luckydraw.quizbot.bot.handler.Handler;
import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateReceiver {

    private final List<Handler> handlers;

    private UserService userService;

    public UpdateReceiver(List<Handler> handlers, @Autowired UserService userService) {
        this.handlers = handlers;
        this.userService = userService;
    }


    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        try {
            if (isMessageWithText(update)) {
                Message message = update.getMessage();

                User user = userService.getInstance(String.valueOf(message.getChatId()));

                if (user == null) userService.registration(new User(Math.toIntExact(message.getChatId())));

                return getHandlerByState(user.getState()).handle(user, message.getText());
            }
            throw new UnsupportedOperationException();

        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }


    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    /*private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }*/


    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
