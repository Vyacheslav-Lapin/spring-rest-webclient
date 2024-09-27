package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.model.PersonClient;
import feign.FeignException.InternalServerError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeignClientTest {

    public static final String ELON_MASK = "Elon Mask";
    @Autowired
    PersonClient personClient;

    @Autowired
    Map<String, Country> countriesMap;

    @Test
    void testGetPerson() {
        Person person = personClient.findPerson(1L).getBody();
        System.out.println(person);

      assertAll(
                () -> assertEquals("John Smith", person.getName()),
                () -> assertEquals(countriesMap.get("UK"), person.getCountry()),
                () -> assertFalse(person.isRegistered()));
    }

    @Test
    void testGetAllPersons() {
        List<Person> persons = personClient.findAll();
        System.out.println(persons);
    }

    @Test
    void testAddPerson() {
        Person person = new Person(ELON_MASK);
        person.setCountry(countriesMap.get("US"));
        person.setIsRegistered(true);
        Person result = personClient.addPerson(person);
        System.out.println(result);

        Person person2 = personClient.findPerson(result.getId()).getBody();
        assertEquals(ELON_MASK, person2.getName());
    }

    @Test
    //@lombok.SneakyThrows
    @DisplayName("Delete endpoint works correctly")
    void deleteEndpointWorksCorrectlyTest() {
        // given
        var person = new Person(ELON_MASK);
        person.setCountry(countriesMap.get("US"));
        person.setIsRegistered(true);

        var result = personClient.addPerson(person);
        var person2 = personClient.findPerson(result.getId()).getBody();
        assertEquals(ELON_MASK, person2.getName());

        // when
        personClient.deletePerson(person2.getId());

        assertThat(assertThrows(InternalServerError.class,
                                () -> personClient.findPerson(person2.getId()))
                       .status())
            // then
            .isEqualTo(500);
    }
}
