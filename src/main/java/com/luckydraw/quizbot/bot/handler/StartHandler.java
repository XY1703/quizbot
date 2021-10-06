package com.luckydraw.quizbot.bot.handler;

import com.luckydraw.quizbot.bot.State;
import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.Collections;
import java.util.List;

import static com.luckydraw.quizbot.util.BotUtil.createMessageTemplate;

@Component
public class StartHandler implements Handler{

    @Value("${telegrambot.name}")
    private String botUsername;

    private UserService service;

    @Autowired
    public StartHandler(UserService service) {
        this.service = service;
    }

    @Override
    public State operatedState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }

    @Override
    public List<Object> handle(User user, String message) {
        //TODO implement message properties
        SendMessage welcome = createMessageTemplate(user);
        SendMessage askName = createMessageTemplate(user);

        welcome.setText(String.format("Привет! Я %s - твой помощник в изучении Java!", botUsername));
        askName.setText("Введите свой никнейм");

        user.setState(State.ENTER_NAME);
        service.save(user);

        return List.of(welcome, askName);
    }
}
