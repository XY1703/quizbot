package com.luckydraw.quizbot.bot.handler;

import com.luckydraw.quizbot.bot.State;
import com.luckydraw.quizbot.model.Theme;
import com.luckydraw.quizbot.model.User;
import com.luckydraw.quizbot.service.ThemeService;
import com.luckydraw.quizbot.service.UserService;
import com.luckydraw.quizbot.util.BotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ThemeHandler implements Handler{
    //TODO move to properties
    protected static final String SHOW_THEMES = "/show_themes";

    private ThemeService themeService;
    private UserService userService;

    @Autowired
    public ThemeHandler(ThemeService themeService, UserService userService) {
        this.themeService = themeService;
        this.userService = userService;
    }

    @Override
    public State operatedState() {
        return State.CHOOSE_THEME;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(SHOW_THEMES);
    }

    @Override
    public List<Object> handle(User user, String message) {
        user.setState(State.CHOOSE_THEME);
        userService.save(user);

        SendMessage sm = BotUtil.createMessageTemplate(user);
        sm.setText("Выберите тему");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        getThemesWithCommand().forEach((k, v) -> rowList.add(List.of(
                                                            BotUtil.createInlineKeyboardButton(k, v))));
        keyboardMarkup.setKeyboard(rowList);
        sm.setReplyMarkup(keyboardMarkup);
        sm.enableHtml(true);

        return List.of(sm);
    }

    private Map<String, String> getThemesWithCommand(){
        List<Theme> themes = themeService.getAll();
        Map<String, String> themesWithCommand = new HashMap<>();

        for(Theme t : themes){
            themesWithCommand.put(t.getName(), themeService.convertThemeNameToCommand(t.getName()));
        }

        return themesWithCommand;
    }



}
