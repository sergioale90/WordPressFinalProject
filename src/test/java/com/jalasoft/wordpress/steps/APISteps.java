package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.controller.APIController;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;

public class APISteps {
    private final APIController controller;

    public APISteps (APIController controller) {
        this.controller = controller;
    }

    @Then("^response should be \"(.*?)\"")
    public void verifyStatusLine(String expected) {
        String actualStatusLine = controller.getResponse().getStatusLine();
        Assert.assertEquals(actualStatusLine, expected, "Wrong Status Line returned");
    }
    @Then("^response should be valid and have a body$")
    public void verifyValidResponseAndBody() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        Assert.assertTrue(Status.SUCCESS.matches(controller.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(controller.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(controller.getResponse().getContentType(), expectedContentType, "Wrong content type returned");
    }

}
