package com.jalasoft.wordpress.steps.hooks.features;

import api.controller.APIController;
import api.methods.APIPostMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIPostFeatureHook {
    private final APIController controller;
    public APIPostFeatureHook(APIController controller) {
        this.controller = controller;
    }
    @Before("@CreateATestPost")
    public void createAPost() {
        Response responseRequest = APIPostMethods.createAPost();
        controller.setResponse(responseRequest);
        Assert.assertTrue(Status.SUCCESS.matches(controller.getResponse().getStatusCode()), "Post was not created");
    }
    @After("@DeleteATestPost")
    public void deleteAPostByID() {
        String id = controller.getResponse().jsonPath().getString("id");
        Response responseRequest = APIPostMethods.deleteAPostById(id);
        Assert.assertTrue(Status.SUCCESS.matches(responseRequest.getStatusCode()), "Post with id --> " + "was not deleted");
    }
}
