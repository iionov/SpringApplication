package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Class for running web application.
 * @author Ionov Ivan.
 */
@SpringBootApplication
class ServingWebContentApplication {
    public static StorageProcessing processing=new StorageProcessing();
    /**
     * At the beginning the user information is read from the file to the List "myUsers".
     */
    public static void main(String[] args) {
        processing.getUsersFromFile(CommandsProcessing.path);
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}