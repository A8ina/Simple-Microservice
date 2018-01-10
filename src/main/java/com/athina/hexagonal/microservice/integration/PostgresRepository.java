package com.athina.hexagonal.microservice.integration;

import com.athina.hexagonal.microservice.business.NamesRepository;
import com.athina.hexagonal.microservice.web.model.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import springfox.documentation.spring.web.json.Json;

import java.sql.*;
import java.util.ArrayList;
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


   // @Bean
    public Connection connection() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        connection =  DriverManager.getConnection(url, user, password);
        System.out.println("Database Opened");
        return  connection;
    }


    @Override
    public Request getName(String name) {
        try {
            connection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String stm = "select json_build_object('name',name,'lastname',lastname,'level',level,'salary',salary)"
                    +"as data from users WHERE NAME = ? ;";

        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name);
            resultSet= pst.executeQuery();
            System.out.println("Edw0");

            while (resultSet.next())
            {
                System.out.println("Edw");
                System.out.println(resultSet.getRow());
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getInt(4));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; //objectMapper.readValues(resultSet, Request.class);
    }

    @Override
    public List<Request> getAll()  {
        try {
            connection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String stm = "select json_build_object('name',name,'lastname',lastname,'level',level,'salary',salary) as data from users;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(stm);
            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1));
            }

            resultSet.close();
            pst.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; //objectMapper.readValues(resultSet, Request.class);
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
            pst.executeUpdate();
            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getName(name.getName());
    }

    @Override
    public List<Request> removeElement(int index) {
        return null;
    }
}
