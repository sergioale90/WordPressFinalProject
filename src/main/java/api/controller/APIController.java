package api.controller;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class APIController {
    private final List<Header> headers = new ArrayList<>();
    private Response response;
    public void addHeader(Header header) {
        headers.add(header);
    }
    public Headers getHeaders() {
        return new Headers(headers);
    }
    public Header getHeader(String headerName) {
        return new Headers(headers).get(headerName);
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public Response getResponse() {
        return response;
    }
}
