package com.jalasoft.wordpress.steps;

import api.APIConfig;
import api.APIManager;
import api.controller.APIController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIPostsSteps {
    private static final APIConfig apiConfig = APIConfig.getInstance();
    private static final APIManager apiManager = APIManager.getInstance();
    private final APIController controller;
    private static final String postsEndpoint = apiConfig.getPostsEndpoint();
    private final String postById = apiConfig.getPostsById();
    private Map <String, Object> params;
    public APIPostsSteps(APIController controller) {
        this.controller = controller;
    }
    @Given("^(?:I make|the user makes) a request to retrieve all posts$")
    public void getAllPosts() {
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = apiManager.get(postsEndpoint, authHeader);
        controller.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve a post$")
    public void getPostById() {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        Response requestResponse = apiManager.get(postById.replace("<id>", id), authHeader);
        Map <String, Object> queryParams = new HashMap<>();
        String content = controller.getResponse().jsonPath().getString("content.raw");
        String title = controller.getResponse().jsonPath().getString("title.raw");
        String status = controller.getResponse().jsonPath().getString("status");
        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("title", title);
        queryParams.put("status", status);
        params = queryParams;
        controller.setResponse(requestResponse);
    }
    @Given("^I make a request to update a post with the following params$")
    public void updatePostById(DataTable table) {
        String id = controller.getResponse().jsonPath().getString("id");
        Header authHeader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);
        Response requestResponse = apiManager.put(postById.replace("<id>", id), queryParams, authHeader);
        params = new HashMap<>(queryParams);
        params.put("id", id);
        controller.setResponse(requestResponse);
    }

    @When("^I make a request to create a post with the following params$")
    public void createTheBodyRequest(DataTable table) {
        Header authheader = controller.getHeader("Authorization");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);
        Response requestPost = apiManager.post(postsEndpoint, queryParams, authheader);
        controller.setResponse(requestPost);
        params = queryParams;
    }
    @Then("^response should have a proper amount of posts$")
    public void verifyPostsAmount() {
        int expectedAmountPosts = Integer.parseInt(controller.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountPosts = controller.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountPosts, expectedAmountPosts, "Wrong amount of posts returned");
    }
    @Then("^post should have been created with the proper params$")
    public void verifyTheParamsOfCreatedPost() {
        String expectedTitle = (String) params.get("title");
        String expectedContent = (String) params.get("content");
        String expectedStatus = (String) params.get("status");
        Response response = controller.getResponse();
        String actualTitle = response.jsonPath().getString("title.raw");
        String actualContent = response.jsonPath().getString("content.raw");
        String actualStatus = response.jsonPath().getString("status");
        Assert.assertTrue(Status.SUCCESS.matches(response.getStatusCode()), "The Post was not created");
        Assert.assertEquals(actualTitle, expectedTitle, "Wrong post title returned");
        Assert.assertEquals(actualContent, expectedContent, "Wrong post content returned");
        Assert.assertEquals(actualStatus, expectedStatus, "Wrong post status returned");
    }
    @Then("^post should have been retrieved with the proper params$")
    public void verifyRetrievedPost() {
        String expectedId = (String) params.get("id");
        String expectedTitle = (String) params.get("title");
        String expectedContent = (String) params.get("content");
        String expectedStatus = (String) params.get("status");
        String actualId = controller.getResponse().jsonPath().getString("id");
        String actualTitle = controller.getResponse().jsonPath().getString("title.rendered");
        String actualContent = controller.getResponse().jsonPath().getString("content.rendered").replaceAll("<[^>]*>", "").strip();
        String actualStatus = controller.getResponse().jsonPath().getString("status");
        Assert.assertEquals(actualId, expectedId);
        Assert.assertTrue(Status.SUCCESS.matches(controller.getResponse().getStatusCode()), "The post was not retrieved");
        Assert.assertEquals(actualTitle, expectedTitle, "Wrong post title returned");
        Assert.assertEquals(actualContent, expectedContent, "Wrong post content returned");
        Assert.assertEquals(actualStatus, expectedStatus, "Wrong post status returned");
    }
    @Then("^post should have been updated with the proper params$")
    public void verifyUpdatePost() {
        String expectedTitle = (String) params.get("title");
        String expectedContent = (String) params.get("content");
        String actualTitle = controller.getResponse().jsonPath().getString("title.raw");
        String actualContent = controller.getResponse().jsonPath().getString("content.raw");
        Assert.assertTrue(Status.SUCCESS.matches(controller.getResponse().getStatusCode()), "The post was not updated");
        Assert.assertEquals(actualTitle, expectedTitle, "The title was nos modified");
        Assert.assertEquals(actualContent, expectedContent, "The content was not modified");
    }
}
