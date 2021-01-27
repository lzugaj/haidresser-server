package com.luv2code.hairdresser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HairdresserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairdresserServerApplication.class, args);
    }

}
