package com.athina.hexagonal.microservice.business;

import com.athina.hexagonal.microservice.web.model.Request;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.List;

@Service
public class NamesService {

    private NamesRepository namesRepository;

    @Inject
    public NamesService( NamesRepository namesRepository) {
        this.namesRepository = namesRepository;
    }


    public Request getName(String name) {

        return namesRepository.getName(name);
    }
    public List<Request> getNames() {

        return namesRepository.getAll();
    }

    public Request addPerson(Request name) {
        return namesRepository.addElement(name);
    }

    public List<Request> updatePerson(String name, Request newPerson){

        namesRepository.getAll().remove(name);
        namesRepository.addElement(newPerson);
        return namesRepository.getAll();
    }


    public List<Request> removeElement(String name){ return  namesRepository.removeElement(name);}

}
