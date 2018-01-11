package com.athina.hexagonal.microservice.integration;

import com.athina.hexagonal.microservice.business.NamesRepository;
import com.athina.hexagonal.microservice.web.model.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.json.JSONArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    private ObjectMapper objectMapper;

    private List<Request> names = new ArrayList<>();

    private PreparedStatement pst = null;
    private Connection connection = null;
    private ResultSet resultSet;
    private Statement statement;


    public Connection connection() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Database Opened");
        return connection;
    }

    @Override
    public Request getName(String name) {

        Request request = new Request();
        try {
            connection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String stm = "select * from users where name = ?;";
        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name);
            resultSet = pst.executeQuery();
            request = requestMapper(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return request;
    }

    @Override
    public List<Request> getAll() {
        List<Request> requestList = new ArrayList<>();

        try {
            connection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String stm = "select * from users;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(stm);
            requestList = requestMapper(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    @Override
    public Request addElement(Request name) {
        try {
            connection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String stm = "INSERT INTO users (name, lastname, level, salary) VALUES(?, ?, ?, ?);";

        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name.getName());
            pst.setString(2, name.getLastName());
            pst.setString(3, name.getLevel());
            pst.setInt(4, name.getSalary());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getName(name.getName());
    }

    @Override
    public List<Request> removeElement(int index) {
        return null;
    }

    public List<Request> requestMapper(ResultSet resultSet) throws SQLException {
        List<Request> requestList = new ArrayList<>();
        Request request = new Request();
        while (resultSet.next()) {
            request.setName(resultSet.getString(1));
            request.setLastName(resultSet.getString(2));
            request.setLevel(resultSet.getString(3));
            request.setSalary(resultSet.getInt(4));
            requestList.add(request);
        }

        return requestList;
    }

}
