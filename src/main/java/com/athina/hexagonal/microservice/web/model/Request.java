package com.athina.hexagonal.microservice.web.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;

public class Request {

    @ApiModelProperty("Person's name")
    private String name;

    @ApiModelProperty("Person's lastname")
    private String lastName;

    @ApiModelProperty("Level of expertise ")
    private String level;

    @ApiModelProperty("Monthly salary")
    @Pattern(regexp = "^[0-9]*$")
    private int salary;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (salary != request.salary) return false;
        if (name != null ? !name.equals(request.name) : request.name != null) return false;
        if (lastName != null ? !lastName.equals(request.lastName) : request.lastName != null) return false;
        return level != null ? level.equals(request.level) : request.level == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + salary;
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level='" + level + '\'' +
                ", salary=" + salary +
                '}';
    }
}
