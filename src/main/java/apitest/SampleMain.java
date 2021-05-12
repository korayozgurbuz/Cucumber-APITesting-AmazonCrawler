package apitest;

import apitest.model.Post;
import apitest.service.UnirestBasedPostService;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SampleMain {

    public static void main(String[] args) {
        UnirestBasedPostService unirestBasedPostService = new UnirestBasedPostService();
        try {
            Post[] posts = unirestBasedPostService.fetchPosts();

            Map<Long, Long> userPostCountMap = new HashMap<Long, Long>();

            for (Post post : posts) {
                long userId = post.getUserId();

                if (userPostCountMap.containsKey(userId)) {
                    Long currentCount = userPostCountMap.get(userId);
                    userPostCountMap.put(userId, currentCount + 1);
                } else {
                    userPostCountMap.put(userId, 1l);
                }
            }

            Set<Long> userIdKeyset = userPostCountMap.keySet();
            for (Long userId : userIdKeyset) {
                Long count = userPostCountMap.get(userId);
                System.out.println("(" + userId + "," + count + ")");
            }


        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
