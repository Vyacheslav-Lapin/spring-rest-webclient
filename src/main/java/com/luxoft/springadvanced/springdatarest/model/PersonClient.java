package com.luxoft.springadvanced.springdatarest.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "persons",
             url = "http://localhost:8081",
             path = "persons")
public interface PersonClient {

    @RequestMapping("/{id}")
    ResponseEntity<Person> findPerson(@PathVariable Long id);

    @RequestMapping
    List<Person> findAll();

    @PostMapping
    Person addPerson(@RequestBody Person person);

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Long id);
}
