package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.Theme;
import org.springframework.stereotype.Service;

@Service
public class ThemeService extends AbstractService<Theme>{

    private static final String URL = "http://localhost:8080/api/themes";

    public ThemeService() {
        url = URL;
        clazz = Theme.class;
    }


}
