package apitest.service;

import apitest.model.Post;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UnirestBasedPostServiceTest {

    private UnirestBasedPostService unirestBasedPostService;


    @Before
    public void setUp() {
        unirestBasedPostService = new UnirestBasedPostService();
    }

    @Test
    public void testUserPostCounts() {

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


            assertEquals("User with id 1 should have 10 posts", 10l, (long) userPostCountMap.get(1l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(2l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(3l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(4l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(5l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(6l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(7l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(8l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(9l));
            assertEquals("User with id 2 should have 10 posts", 10l, (long) userPostCountMap.get(10l));


        } catch (UnirestException e) {
            fail("Test failed with an exception");
            e.printStackTrace();
        }

    }

    @Test
    public void testUniqueness() {

        try {
            Post[] posts = unirestBasedPostService.fetchPosts();

            Map<Long, Long> postIdCountmap = new HashMap<Long, Long>();

            for (Post post : posts) {
                long userId = post.getId();

                if (postIdCountmap.containsKey(userId)) {
                    Long currentCount = postIdCountmap.get(userId);
                    postIdCountmap.put(userId, currentCount + 1);
                } else {
                    postIdCountmap.put(userId, 1l);
                }
            }

            Set<Long> idKeyset = postIdCountmap.keySet();
            for (Long id : idKeyset) {
                Long count = postIdCountmap.get(id);
                if (count != 1) {
                    fail("All posts do not have a unique id");
                }

            }


        } catch (UnirestException e) {
            fail("Test failed with an exception");
            e.printStackTrace();
        }

    }
}