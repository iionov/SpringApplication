package com.example.demo;

import com.google.gson.*;
import exeptions.NotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.ValidationException;
import java.io.*;
import java.util.*;

@RequestMapping()
@RestController
public class GreetingController {
public static JsonArray myusers=new JsonArray();
public static List<Map<String,String>> myUsers =new ArrayList<Map<String, String>>();

    @PostMapping(value = "/Users")
    public ResponseEntity<String> createNewUser (@RequestBody User user) throws ValidationException, IOException {
        Gson gson=new Gson();
        String userInJsonString =gson.toJson(user);
        StorageProcessing.transformationJsonElementToMap(userInJsonString);
        StorageProcessing.writeToJsonNewUser(userInJsonString);
        return new ResponseEntity<String>(userInJsonString, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Map<String, String> showUser(@PathVariable String id){
        System.out.println(">>"+myUsers+"<<");
    return findUserInList(id);
    }

    public Map<String, String> findUserInList(@PathVariable String id) {
        System.out.println("userToString ");
        return myUsers.stream().filter(user -> user.get("id").equals(id)).findFirst().orElseThrow(NotFoundExeption::new);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id) throws FileNotFoundException, UnsupportedEncodingException {
       myUsers.remove(findUserInList(id)) ;
       StorageProcessing.deleteUserInFile(id);
       System.out.println(">>"+myUsers+"<<");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Map<String,String> updateUser(@PathVariable String id, @RequestBody Map<String,String> updateInfo){
         Map<String, String> user=findUserInList(id);
         user.putAll(updateInfo);
         user.put("id", id);
         StorageProcessing.writeToFileAfterUpdatingUser();
    return user;
    }
}