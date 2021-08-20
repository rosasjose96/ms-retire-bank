package com.example.msretire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The type Ms retire application.
 */
@SpringBootApplication
@EnableEurekaClient
public class MsRetireApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MsRetireApplication.class, args);
    }

}
