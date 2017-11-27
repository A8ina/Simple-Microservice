package com.athina.hexagonal.microservice.business;

import com.athina.hexagonal.microservice.web.model.Request;

import java.util.List;

public interface NamesRepository {
    Request getName(int index);
    List<Request> getAll();
    Request addElement(Request name);
    List<Request> removeElement(int index);
}
