package com.luxoft.springadvanced.springdatarest.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long id) {
        super("Person id not found : " + id);
    }

}
