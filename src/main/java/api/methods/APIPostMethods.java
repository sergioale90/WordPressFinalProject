package api.methods;

import api.APIConfig;
import api.APIManager;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.logging.log4j.core.LoggerContext;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;

public class APIPostMethods {
    public static final LoggerManager logger = LoggerManager.getInstance();
    public static final APIManager apiManager = APIManager.getInstance();
    public static final APIConfig apiConfig = APIConfig.getInstance();
    public static Response createAPost() {
        String postEndPoint = apiConfig.getPostsEndpoint();
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("content", "Test API Post Content");
        jsonAsMap.put("title", "Test API Title");
        jsonAsMap.put("status", "publish");
        return apiManager.post(postEndPoint, jsonAsMap, authHeader);
    }
    public static Response deleteAPostById(String id) {
        String postById = apiConfig.getPostsById().replace("<id>", id);
        Header authHeader = APIAuthMethods.getAuthHeader("administrator");
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);
        return apiManager.delete(postById, jsonAsMap, authHeader);
    }
}
