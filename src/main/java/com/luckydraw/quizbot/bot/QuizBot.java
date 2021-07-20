package com.luckydraw.quizbot.bot;

import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.ThemeService;
import com.luckydraw.quizbot.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        /*SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(update.getMessage().getChatId()));
        sm.setText(userService.getInstance(update.getMessage().getText()).toString());
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/

        User user = new User(Math.toIntExact(update.getMessage().getChatId()));
        userService.registration(user);


    }
}
