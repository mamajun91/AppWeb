package com.appweb.service;


import com.appweb.custom.CustomProperties;
import com.appweb.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    private CustomProperties properties;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Person> getAllPersons() {
        String apiUrl = properties.getApilnr() + "/persons";
        return  restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>() {}
        ).getBody();
    }

    public Person getPersonById(Integer id){
        String apiUrl = properties.getApilnr() + "/person/" + id;
        return  restTemplate.getForObject(apiUrl, Person.class);
    }

    public Person createPerson(Person person){
        String apiUrl = properties.getApilnr() + "/person";
        return restTemplate.postForObject(apiUrl, person, Person.class);
    }

    public Person updatePerson (Integer id, Person person){
        String apiUrl = properties.getApilnr() + "/person/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Person> resquestEntity = new HttpEntity<>(person, headers);

        ResponseEntity<Person> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                resquestEntity,
                Person.class
        );
        return response.getBody();
    }

    public void deletePerson(Integer id){
        String apiUrl = properties.getApilnr() + "/person/" + id;
        restTemplate.delete(apiUrl);
    }


}
