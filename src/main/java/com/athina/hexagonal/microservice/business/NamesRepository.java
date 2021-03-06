package com.athina.hexagonal.microservice.business;

import com.athina.hexagonal.microservice.web.model.Request;

import java.sql.SQLException;
import java.util.List;

public interface NamesRepository {

    Request getName( String name);
    List<Request> getAll();
    Request addElement(Request name);
    List<Request> removeElement(String name);
}
