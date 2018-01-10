//package com.athina.hexagonal.microservice.integration;
//
//import com.athina.hexagonal.microservice.business.NamesRepository;
//import com.athina.hexagonal.microservice.web.model.Request;
//import com.athina.hexagonal.microservice.web.model.Response;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//
//@Repository
//public class InMemoryNamesRepository implements NamesRepository {
//
//    private List<Request> names = new ArrayList<>();
//
//    @Override
//    public Request getName(String name) {
//
//        return names.stream().filter(item -> item.getName().equals(name)).findFirst().get();
//
//    }
//
//    @Override
//    public List<Request> getAll() {
//        return names;
//
//    }
//
//    @Override
//    public Request addElement(Request name) {
//        // if name already exists - don't re-insert it
//        if (names.contains(name)) {
//            return null;
//        }
//
//        // add the name and return it in case it is successfully added
//        names.add(name);
//        return names.get(names.indexOf(name));
//    }
//
//    @Override
//    public List<Request> removeElement(int index) {
//
//        names.remove(index);
//        return names.stream().sorted().collect(Collectors.toList());
//    }
//
//
//}
