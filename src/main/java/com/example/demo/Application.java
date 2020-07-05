package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class for running web application.
 *
 * @author Ionov Ivan.
 */

@SpringBootApplication
class ServingWebContentApplication {

    public static StorageProcessing processing;
    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml"
    );


    /**
     * At the beginning the user information is read from the file to the List "myUsers".
     */
    public static void main(String[] args) {
        processing = context.getBean("storageProcessing", StorageProcessing.class);
        processing.getUsersFromFile(UsersController.path);
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}