package com.luckydraw.quizbot.bot.handler;

import com.luckydraw.quizbot.bot.State;
import com.luckydraw.quizbot.model.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface Handler {

    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);

    State operatedBotState();

    List<String> operatedCallBackQuery();
}
