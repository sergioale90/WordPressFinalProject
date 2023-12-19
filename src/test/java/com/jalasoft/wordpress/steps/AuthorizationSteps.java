package com.jalasoft.wordpress.steps;

import api.controller.APIController;
import api.methods.APIAuthMethods;
import io.cucumber.java.en.Given;
import io.restassured.http.Header;
import org.testng.Assert;

public class AuthorizationSteps {
    private final APIController controller;
    public AuthorizationSteps(APIController controller) {
        this.controller = controller;
    }
    @Given ("^(?:I am|the user is) authenticated with \"(.*?)\" role$")
    public void getToken(String userRole) {
        Header autHeader = APIAuthMethods.getAuthHeader(userRole);
        controller.addHeader(autHeader);
        Assert.assertNotNull(autHeader, "Unable to retrieve authorization header for user with role --> " + userRole);
    }
}
