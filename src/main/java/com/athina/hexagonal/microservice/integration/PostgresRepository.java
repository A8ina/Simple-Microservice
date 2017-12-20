package com.athina.hexagonal.microservice.integration;

import com.athina.hexagonal.microservice.business.NamesRepository;
import com.athina.hexagonal.microservice.web.model.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PostgresRepository implements NamesRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    private List<Request> names = new ArrayList<>();

    public void connection() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        DriverManager.getConnection(url, user, password);
        System.out.println("Opened database successfully");
    }


    @Override
    public Request getName(int index) {
        return null;
    }

    @Override
    public List<Request> getAll()  {
        return null;
    }

    @Override
    public Request addElement(Request name) {
        return null;
    }

    @Override
    public List<Request> removeElement(int index) {
        return null;
    }
}
