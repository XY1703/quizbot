package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Theme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeService extends AbstractService<Theme>{

    private static final String URL = "http://localhost:8080/api/themes";

    public ThemeService() {
        url = URL;
        clazz = Theme.class;
    }

    public List<Theme> getAll() {
        return super.getAll(URL);
    }

    public String convertThemeNameToCommand(String name){
        return name.trim().toLowerCase().replaceAll(" ", "_");
    }

    public List<String> getThemesListAsCommand(List<Theme> themes){
        List<String> commands = new ArrayList<>();
        for(Theme t : themes) commands.add(convertThemeNameToCommand(t.getName()));

        return commands;
    }
}
