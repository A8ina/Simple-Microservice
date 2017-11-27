package com.athina.hexagonal.microservice.web;

import com.athina.hexagonal.microservice.business.NamesService;
import com.athina.hexagonal.microservice.web.model.Request;
import com.athina.hexagonal.microservice.web.model.Response;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/microservice")
@Api
public class Endpoint {

    @Inject
    private NamesService namesService;

    @GetMapping
    public Response getAllnames() {

        Response response = new Response();
        response.setData(namesService.getNames());
        return response;
    }

    @GetMapping(value = "/{name}")
    public Response getName(@PathVariable("name") String name) {

        int index = findPersonIndex(name);
         Request request =  namesService.getNames().get(index);
        Response response = new Response();
        response.setData(Arrays.asList(request));
        return response;
    }

    @PostMapping
    public Response addName(@RequestBody Request request) {
        Request addedName = namesService.addPerson(request);

        Response response = new Response();

        if(addedName==null) {
            response.setError("Person already exists");
            response.setData(getAllnames().getData());
        }
        else {
            response.setData(Arrays.asList(addedName));
        }

        return response;
    }

    @DeleteMapping(value = "/{name}")
    public Response deleteName( @PathVariable("name") String name) {
        Response response = new Response();
        int index = findPersonIndex(name);

        List<Request> persons = namesService.removeElement(index);

        response.setData(persons);

        return response;
    }

    @PatchMapping(value = "/{name}")
    public Response updatePerson(@RequestBody Request person,
                                 @PathVariable("name") String name){

        int index = findPersonIndex(name);
        List<Request> persons = namesService.updatePerson(index,person);

        Response response = new Response();
        response.setData(persons);

        return response;
    }


    public int findPersonIndex(String name) throws NoSuchElementException {
        Request toDelete = namesService.getNames().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .get();

        int index = namesService.getNames().indexOf(toDelete);
        return index;
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public Response fillError(NoSuchElementException ex) {
        Response response = new Response();
        response.setError("No such Person");

        return response;
    }
}
