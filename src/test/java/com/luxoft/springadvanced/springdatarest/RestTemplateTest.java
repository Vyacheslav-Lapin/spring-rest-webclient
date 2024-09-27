package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestTemplateTest {
    String resourceUrl = "http://localhost:8081/persons";

    @Test
    void testGetForEntity() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person> response = restTemplate.getForEntity(
                resourceUrl + "/1", Person.class);
        assertThat(response.getStatusCode(),
                equalTo(HttpStatus.OK));
        System.out.println(response.getBody().getName());
    }

    @Test
    void testGetForObject() {
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(
                resourceUrl + "/1", Person.class);
        assertThat(person.getName(), notNullValue());
    }

    @Test
    void testRetrieveHeaders() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders =
                restTemplate.headForHeaders(resourceUrl);
        assertTrue(httpHeaders.getContentType().
                includes(MediaType.APPLICATION_JSON));
    }

    @Test
    void testPost() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Person> request =
                new HttpEntity<>(new Person("John Smith"));
        Person person =
                restTemplate.postForObject(resourceUrl,
                        request, Person.class);
        assert person != null;
        assertThat(person.getId(), notNullValue());
        assertThat(person.getName(), is("John Smith"));

        Person person2 = restTemplate.getForObject(
                resourceUrl + "/"+person.getId(), Person.class);
        assert person2 != null;
        assertEquals(person.getId(), person2.getId());
        assertEquals(person.getName(), person2.getName());
    }
}
