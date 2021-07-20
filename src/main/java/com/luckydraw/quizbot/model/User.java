package com.luckydraw.quizbot.model;

import com.luckydraw.quizbot.bot.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    private Integer chatId;

    private String name;

    private State state;

    public User(Integer chatId) {
        this.chatId = chatId;
        this.name = String.valueOf(chatId);
        this.state = State.START;
    }
}
