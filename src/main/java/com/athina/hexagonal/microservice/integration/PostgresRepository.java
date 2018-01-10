package com.athina.hexagonal.microservice.integration;

import com.athina.hexagonal.microservice.business.NamesRepository;
import com.athina.hexagonal.microservice.web.model.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.sql.DataSourceDefinition;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    private PreparedStatement pst = null;
    private Connection connection = null;

   // @Bean
    public Connection connection() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
       //DriverManager.register(new org.postgresql.Driver());
        connection =  DriverManager.getConnection(url, user, password);
        System.out.println("Database Opened");
        return  connection;
    }


    @Override
    public Request getName(String name) {
        return null;
    }

    @Override
    public List<Request> getAll()  {
        return null;
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
        String stm = "INSERT INTO users (name, lastname, level, salary) VALUES(?, ?, ?, ?)";

        try {
            pst = connection.prepareStatement(stm);
            pst.setString(1, name.getName());
            pst.setString(2, name.getLastName());
            pst.setString(3, name.getLevel());
            pst.setInt(4, name.getSalary());
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; //getName(name.getName());
    }

    @Override
    public List<Request> removeElement(int index) {
        return null;
    }
}
