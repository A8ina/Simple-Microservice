package com.athina.hexagonal.microservice.web;

import com.athina.hexagonal.microservice.business.NamesService;
import com.athina.hexagonal.microservice.web.model.Request;
import com.athina.hexagonal.microservice.web.model.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/microservice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api
public class Endpoint {

    @Inject
    private NamesService namesService;

    @GetMapping
    @ApiOperation( value = "Show all Persons details")
    public Response getAllnames() {

        Response response = new Response();
        response.setData(namesService.getNames());
        return response;
    }

    @GetMapping(value = "/{name}")
    @ApiOperation( value = "Show Person's details")
    public Response getName(@PathVariable("name") String name) {

        Request request =  namesService.getName(name);
        Response response = new Response();
        response.setData(Arrays.asList(request));
        return response;
    }

    @PostMapping
    @ApiOperation( value = "Add Person")
    public Response addName(@RequestBody Request request) {

        Request addedName = namesService.addPerson(request);
        Response response = new Response();

        // This is not necessary for Postgres Integration
        if(addedName==null) {
            response.setError("Person already exists");
            response.setData(getAllnames().getData());
        }
        else response.setData(Arrays.asList(addedName));

      return response;
    }

    @DeleteMapping(value = "/{name}")
    @ApiOperation( value = "Remove Person")
    public Response deleteName( @PathVariable("name") String name) {
        Response response = new Response();
        List<Request> persons = namesService.removeElement(name);
        response.setData(persons);
        return response;
    }

    @PatchMapping(value = "/{name}")
    @ApiOperation( value = "Update Person's details")
    public Response updatePerson(@RequestBody Request person,
                                 @PathVariable("name") String name){

        List<Request> persons = namesService.updatePerson(name, person);

        Response response = new Response();
        response.setData(persons);

        return response;
    }


    @ExceptionHandler(value = NoSuchElementException.class)
    public Response fillError(NoSuchElementException ex) {
        Response response = new Response();
        response.setError("No such Person");

        return response;
    }
}
