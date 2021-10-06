package com.luckydraw.quizbot.model;

import com.luckydraw.quizbot.bot.State;
import lombok.*;


@Builder
@Data
public class User {

    private Long id;

    private Long chatId;

    private String name;

    private State state;

    public User(Long chatId) {
        this.chatId = chatId;
        this.name = String.valueOf(chatId);
        this.state = State.START;
    }

    @Override
    public String toString() {
        //TODO should be refactored
        String withId = String.format("id: %s \n chatId: %s \n name: %s \n state: %s", id, chatId, name, state);
        String withoutId = String.format("\n chatId: %s  \n name: %s \n state: %s", chatId, name, state);

        return id == null ? withoutId : withId;
    }
}
