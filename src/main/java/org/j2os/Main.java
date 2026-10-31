package org.j2os;

import org.j2os.account.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.addListeners(new ApplicationPidFileWriter("process.id"));

        springApplication.run();
    }

    @Bean
    CommandLineRunner startup(PersonService personService1, PersonService personService2) {
        return (environment) ->{
            personService1.save();
        };
    }
}