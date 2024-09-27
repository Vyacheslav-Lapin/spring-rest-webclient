package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.beans.CsvDataLoader;
import com.luxoft.springadvanced.springdatarest.model.Country;
import com.luxoft.springadvanced.springdatarest.model.CountryRepository;
import com.luxoft.springadvanced.springdatarest.model.Person;
import com.luxoft.springadvanced.springdatarest.model.PersonRepository;
import feign.Client;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
@Import(CsvDataLoader.class)
public class Application {

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }

    @Autowired
    private Map<String, Country> countriesMap;

    @Autowired
    List<Person> persons;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ApplicationRunner configureRepository(CountryRepository countryRepository,
                                          PersonRepository personRepository) {
        return args -> {

            for (Country country : countriesMap.values()) {
                countryRepository.save(country);
            }

            for (Person person : persons) {
                personRepository.save(person);
            }
        };
    }

//    @Bean
//    public FilterRegistrationBean someFilterRegistration() {
//        FilterRegistrationBean<ShallowEtagHeaderFilter> registration
//                = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("etagFilter");
//        return registration;
//    }

    @Bean(name = "etagFilter")
    public Filter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
