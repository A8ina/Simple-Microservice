package com.athina.hexagonal.microservice.web.model;

import java.util.List;

public class Response {
    private String error;
    private List<Request> data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Request> getData() {
        return data;
    }

    public void setData(List<Request> data) {
        this.data = data;
    }
}
