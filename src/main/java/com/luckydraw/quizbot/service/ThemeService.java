package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Theme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeService extends AbstractService<Theme>{
    //TODO move to properties
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

    public List<String> getThemesListAsCommand(){
        List<String> commands = new ArrayList<>();
        for(Theme t : getAll()) commands.add(convertThemeNameToCommand(t.getName()));

        return commands;
    }

    public Theme convertCommandToTheme(String command){
        List<Theme> themes = getAll();

        for(Theme t : themes){
            if(convertThemeNameToCommand(t.getName()).equals(command)) return t;
        }

        return null;
    }


}
