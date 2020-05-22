package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.demo.GreetingController.myUsers;

@SpringBootApplication
class ServingWebContentApplication {

    public static void main(String[] args) {
        StorageProcessing.getUsersFromFile();
        System.out.println(">>"+myUsers+"<<");
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}