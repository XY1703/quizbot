package com.luckydraw.quizbot.bot.handler;

import com.luckydraw.quizbot.bot.State;
import com.luckydraw.quizbot.model.User;

import java.util.List;

public interface Handler {

    State operatedState();

    List<String> operatedCallBackQuery();

    List<Object> handle(User user, String message);
}
