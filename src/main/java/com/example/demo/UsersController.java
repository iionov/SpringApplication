package com.example.demo;

import com.google.gson.*;
import exeptions.NotFoundExeption;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.io.*;
import java.util.*;

import static com.example.demo.ServingWebContentApplication.processing;


/**
 * Class for processing commands from client
 */
@RestController
public class UsersController {

    public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml"
    );
    public static JsonArray myUsersJsonArray = new JsonArray();
    public static List<Map<String, String>> myUsers = new ArrayList<>();
    public static String path = "src\\main\\resources\\templates\\Users.json";

    /**
     * @param user information about new user (in JSON), which client passes to the server.
     * @return information about user, that was created.
     * @throws ValidationException This exception indicates that an error has occurred while performing a validate operation.
     */
    @PostMapping(value = "/Users")
    private ResponseEntity<String> createNewUser(@RequestBody User user) throws ValidationException {
        Gson gson = new Gson();
        String userInJsonString = gson.toJson(user);
        processing.transformationJsonElementToMap(userInJsonString);
        processing.writeToJsonNewUser(userInJsonString, path);
        return new ResponseEntity<String>(userInJsonString, HttpStatus.OK);
    }

    /**
     * @param id user's id, that client wants to view.
     * @return information about user.
     */
    @GetMapping("{id}")
    private Map<String, String> showUser(@PathVariable String id) {
        return findUserInList(id);

    }

    /**
     * Finding user in database.
     *
     * @param id user's id, that needs to find.
     * @return information about user.
     */
    private Map<String, String> findUserInList(@PathVariable String id) {
        return myUsers.stream()
                .filter(user -> user.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundExeption::new);
    }

    /**
     * @param id user's id, that needs to delete.
     * @throws FileNotFoundException signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    @DeleteMapping("{id}")
    private void deleteUser(@PathVariable String id) throws FileNotFoundException {
        myUsers.remove(findUserInList(id));
        processing.deleteUserInFile(path);
    }

    /**
     * @param id         user's id, that needs to update.
     * @param updateInfo new information about user (in JSON).
     * @return updated information about user.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    private Map<String, String> updateUser(@PathVariable String id, @RequestBody Map<String, String> updateInfo) {
        Map<String, String> user = findUserInList(id);
        user.putAll(updateInfo);
        user.put("id", id);
        processing.writeToFileAfterUpdatingUser(path);
        return user;
    }
}