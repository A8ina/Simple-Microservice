package com.athina.hexagonal.microservice.web.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Request {

    @ApiModelProperty("Person's name")
    private String name;

    @ApiModelProperty("Person's lastname")
    private String lastname;

    @ApiModelProperty("Level of expertise ")
    private String level;

    @ApiModelProperty("Monthly salary")
    @Pattern(regexp = "^[0-9]*$")
    private int salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return salary == request.salary &&
                Objects.equals(name, request.name) &&
                Objects.equals(lastname, request.lastname) &&
                Objects.equals(level, request.level);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, lastname, level, salary);
    }

    public String getLastname() {

        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", level='" + level + '\'' +
                ", salary=" + salary +
                '}';
    }
}
