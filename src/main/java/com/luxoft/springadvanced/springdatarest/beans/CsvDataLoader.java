package com.luxoft.springadvanced.springdatarest.beans;

import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.Person;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvDataLoader {

    private final Map<String, Country> countriesMap = new HashMap<>();

    public CsvDataLoader() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/countries_information.csv"))) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] countriesString = line.toString().split(";");
                    Country country = new Country(countriesString[0].trim(), countriesString[1].trim());
                    countriesMap.put(countriesString[1].trim(), country);
                }
            } while (line != null);
        }
    }

    @Bean
    Map<String, Country> countriesMap() {
        return Collections.unmodifiableMap(countriesMap);
    }

    @Bean
    public List<Person> buildPersonsFromCsv() throws IOException {
        List<Person> persons = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/persons_information.csv"))) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] personString = line.toString().split(";");
                    Person person = new Person(personString[0].trim());
                    person.setCountry(countriesMap.get(personString[1].trim()));
                    person.setIsRegistered(false);
                    persons.add(person);
                }
            } while (line != null);

        }

        return persons;
    }
}
