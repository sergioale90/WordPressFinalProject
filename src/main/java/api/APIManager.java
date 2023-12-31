package api;

import framework.CredentialsManager;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.LoggerManager;

import java.util.Map;

public class APIManager {
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static final APIConfig apiConfig = APIConfig.getInstance();
    private static APIManager instance;
    private APIManager() {
        initialize();
    }
    public static APIManager getInstance() {
        if (instance == null) {
            instance = new APIManager();
        }
        return instance;
    }
    private void initialize() {
        log.info("Initializing API Manager");
        RestAssured.baseURI = credentialsManager.getBaseURL();
        RestAssured.basePath = APIConfig.getInstance().getBasePath();
        RestAssured.port = apiConfig.getAPIServicePort();
    }
    //use this method for basic authentication
    public void setCredentials(String userRole) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(credentialsManager.getUsername(userRole));
        authScheme.setPassword(credentialsManager.getPassword(userRole));
        RestAssured.authentication = authScheme;
    }
    public Response get(String endpoint) {
        return RestAssured.given().get(endpoint);
    }
    public Response get (String endpoint, Headers headers) {
        return RestAssured.given().headers(headers).get(endpoint);
    }
    public Response get (String endpoint, Header header) {
        return RestAssured.given().header(header).get(endpoint);
    }
    public Response post(String endpoint, ContentType contentType, Object object) {
        return RestAssured.given().contentType(contentType).body(object).post(endpoint);
    }
    public Response post(String endpoint, Headers headers, ContentType contentType, Object object) {
        return RestAssured.given().headers(headers).contentType(contentType).body(object).post(endpoint);
    }
    public Response post(String endpoint, Header header, ContentType contentType, Object object) {
        return RestAssured.given().header(header).contentType(contentType).body(object).post(endpoint);
    }
    public Response post(String endpoint, Map<String,Object> queryParams, Headers headers) {
        return RestAssured.given().queryParams(queryParams).headers(headers).post(endpoint);
    }
    public Response post(String endpoint, Map<String,Object> queryParams, Header header) {
        return RestAssured.given().queryParams(queryParams).header(header).post(endpoint);
    }
    public Response put(String endpoint, Headers headers) {
        return RestAssured.given().headers(headers).put(endpoint);
    }
    public Response put(String endpoint, Header header) {
        return RestAssured.given().header(header).put(endpoint);
    }
    public Response put(String endpoint, Map<String,Object> queryParams, Headers headers) {
        return RestAssured.given().queryParams(queryParams).headers(headers).put(endpoint);
    }
    public Response put(String endpoint, Map<String,Object> queryParams, Header header) {
        return RestAssured.given().queryParams(queryParams).header(header).put(endpoint);
    }
    public Response delete(String endpoint, Headers headers) {
        return RestAssured.given().headers(headers).delete(endpoint);
    }
    public Response delete(String endpoint, Header header) {
        return RestAssured.given().header(header).delete(endpoint);
    }
    public Response delete(String endpoint, Map<String,Object> queryParams, Headers headers) {
        return RestAssured.given().queryParams(queryParams).headers(headers).delete(endpoint);
    }
    public Response delete(String endpoint, Map<String,Object> queryParams, Header header) {
        return RestAssured.given().queryParams(queryParams).header(header).delete(endpoint);
    }
}
