package com.luckydraw.quizbot.service;

import com.luckydraw.quizbot.model.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class UserService extends AbstractService<User> {

    //TODO move to properties
    private static final String URL = "http://localhost:8080/api/user/";

    public UserService() {
        url = URL;
        clazz = User.class;
    }


    public void registration(User user) {
        JSONObject object = new JSONObject();
        object.put("chatId", String.valueOf(user.getChatId()));
        object.put("name", user.getName());
        object.put("state", user.getState());


        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(URL + "new");
            StringEntity params = new StringEntity(object.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public User getByChatId(Long id){
        final RestTemplate template = new RestTemplate();
        User user = template.getForObject(URL + id, User.class);

        return user;
    }
}
