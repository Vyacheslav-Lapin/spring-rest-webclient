package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WebClientPersonsBuilderTest {

    @Test
    void testGetPerson() {
        WebClient webClient = WebClient.builder()
           .baseUrl("http://localhost:8081")
           .defaultHeader(HttpHeaders.CONTENT_TYPE,
                          MediaType.APPLICATION_JSON_VALUE)
           .defaultUriVariables(Collections.singletonMap("url",
                   "http://localhost:8081"))
           .build();

        ClientResponse clientResponse = webClient.get()
                .uri("/persons/1")
                .exchange()
                .block();
        ClientResponse.Headers headers = clientResponse.headers();

        Person person = clientResponse.body(
                BodyExtractors.toMono(Person.class)).block();
        System.out.println(person);

        assertAll(
            () -> assertEquals("John Smith", person.getName()),
            () -> assertFalse(person.isRegistered()),
            () -> assertEquals(MediaType.APPLICATION_JSON, headers.contentType().get()),
            () -> assertEquals(HttpStatus.OK, clientResponse.statusCode()));
    }


}
