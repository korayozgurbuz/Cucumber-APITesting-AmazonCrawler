package apitest.service;

import apitest.model.Post;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UnirestBasedPostService {

    private Gson gson = new Gson();


    public Post[] fetchPosts() throws UnirestException {
        HttpResponse<String> httpResponse = Unirest.get("https://jsonplaceholder.typicode.com/posts")
                .asString();

        String body = httpResponse.getBody();
        Post[] posts = gson.fromJson(body, Post[].class);

        return posts;
    }

}
