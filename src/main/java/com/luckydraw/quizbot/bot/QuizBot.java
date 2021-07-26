package com.luckydraw.quizbot.bot;

import com.luckydraw.quizbot.service.ThemeService;
import com.luckydraw.quizbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

//TODO move Autowired into constructor
@Component
public class QuizBot extends TelegramLongPollingBot {

    @Autowired
    private ThemeService service;

    @Autowired
    private UserService userService;

    @Value("${telegrambot.name}")
    private String userName;

    @Value("${telegrambot.token}")
    private String token;

    private final UpdateReceiver updateReceiver;

    public QuizBot(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        List<Object> messagesToSend = updateReceiver.handleUpdate(update);

        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
