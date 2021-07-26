package com.luckydraw.quizbot.model;

import com.luckydraw.quizbot.bot.State;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

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
        return String.format("\n id: %s \n name: %s \n state: %s", chatId, name, state);
    }
}
