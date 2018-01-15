package com.athina.hexagonal.microservice.integration;

import com.athina.hexagonal.microservice.business.NamesRepository;
import com.athina.hexagonal.microservice.web.model.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class PostgresRepository implements NamesRepository {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Connection connection = null;

    private List<Request> names = new ArrayList<>();
    private PreparedStatement pst = null;
    private ResultSet resultSet;
    private Statement statement;



    @Override
    public Request getName(String name) {

        String stm = "select * from users where name = ?;";
        Request request = new Request();
        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name);
            resultSet = pst.executeQuery();
            request = requestMapper(resultSet)
                    .stream()
                    .filter(item -> item.getName().equals(name))
                    .findFirst()
                    .get();

            pst.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

       return request;
    }

    @Override
    public List<Request> getAll() {

        String stm = "select * from users;";
        List<Request> requestList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(stm);
            requestList = requestMapper(resultSet);

            statement.close();
            resultSet.close();
         } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    @Override
    public Request addElement(Request name) {

        Request request;
        String stm = "INSERT INTO users (name, lastname, level, salary) VALUES(?, ?, ?, ?)"
                +"ON CONFLICT (name,lastname) DO UPDATE SET level = ?, salary =?";

        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name.getName());
            pst.setString(2, name.getLastname());
            pst.setString(3, name.getLevel());
            pst.setInt(4, name.getSalary());
            pst.setString(5, name.getLevel());
            pst.setInt(6, name.getSalary());
            pst.executeUpdate();

            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace(); }
        finally {
            request = null;
        }

        request = getAll()
                .stream()
                .filter(item -> item.getName().equals(name.getName()))
                .findFirst()
                .get();
        return request;
    }

    @Override
    public List<Request> removeElement(String name) {
        String stm = "Delete from users where name = ?;";
        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name);
            pst.executeUpdate();

            pst.close();
            resultSet.close();
            connection.close();
         } catch (SQLException e) {
            e.printStackTrace();
        }
        return  getAll();
    }

    public List<Request> requestMapper(ResultSet resultSet) throws SQLException {
        List<Request> requestList = new ArrayList<>();

        while (resultSet.next()) {
            Request request = new Request();
            request.setName(resultSet.getString(1));
            request.setLastname(resultSet.getString(2));
            request.setLevel(resultSet.getString(3));
            request.setSalary(resultSet.getInt(4));
            requestList.add(request);
        }

        return requestList;
    }

}
