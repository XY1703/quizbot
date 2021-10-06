package com.luckydraw.quizbot.bot;


import com.luckydraw.quizbot.bot.handler.Handler;
import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;


@Component
public class UpdateReceiver {

    private final List<Handler> handlers;
    private final UserService service;

    @Autowired
    public UpdateReceiver(List<Handler> handlers, UserService service) {
        this.handlers = handlers;
        this.service = service;
    }

    public List<Object> handleUpdate(Update update){
        try{
            if(isMessageWithText(update)){
                final Message message = update.getMessage();
                final Long chatId = message.getFrom().getId();

                final User user = getCheckedUser(chatId);

                return getHandlerByState(user.getState()).handle(user, message.getText());
            }else if(update.hasCallbackQuery()){
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final Long chatId = callbackQuery.getFrom().getId();

                final User user = getCheckedUser(chatId);

                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }
            throw new UnsupportedOperationException();

        }catch (UnsupportedOperationException e){
            return Collections.emptyList();
        }
    }

    private User getCheckedUser(Long chatId){
        User user = service.getByChatId(chatId);

        if(user == null){
            user = new User(chatId);
            service.save(user);
        }
        return user;
    }

    private boolean isMessageWithText(Update update){
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(h -> h.operatedState() != null)
                .filter(h -> h.operatedState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
