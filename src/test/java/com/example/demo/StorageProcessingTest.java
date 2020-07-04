package com.example.demo;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static com.example.demo.UsersController.myUsersJsonArray;
import static org.junit.Assert.*;

public class StorageProcessingTest {
    public String path = "src\\test\\java\\resources\\UsersTest.json";
    public String example = "{\"name\":" + "\"" + "attribute1" + "\"" + ",\"familyName\":" + "\"" + "attribute2" +
            "\"" + ",\"birthday\":" + "\"" + "attribute3" + "\"" + ",\"id\":" + "\"" + "attribute4" + "\"" + "}";
    static List<Map<String, String>> myUsers = new ArrayList<>();

    @BeforeClass
    public static void add() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"name\":" + "\"" + "attribute1" + "\"" + ",\"familyName\":" + "\"" + "attribute2" +
                "\"" + ",\"birthday\":" + "\"" + "attribute3" + "\"" + ",\"id\":" + "\"" + "attribute4" + "\"" + "}";
        try {

            Map<String, String> jsonInMap = mapper.readValue(jsonString,
                    new TypeReference<Map<String, String>>() {
                    });
            myUsers.add(jsonInMap);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUsersFromFileTest() {
        StorageProcessing storageProcessing = new StorageProcessing();
        storageProcessing.getUsersFromFile(path);
        assertEquals(2, myUsersJsonArray.size());
    }

    @Test
    public void writeToJsonNewUserTest() {
        StorageProcessing storageProcessing = new StorageProcessing();
        storageProcessing.getUsersFromFile(path);
        int lengthBefore = myUsersJsonArray.size();
        storageProcessing.writeToJsonNewUser(example, path);
        storageProcessing.getUsersFromFile(path);
        int lengthAfter = myUsersJsonArray.size();
        assertEquals(lengthBefore + 1, lengthAfter);
    }


    @Test
    public void transformationJsonElementToMapTest() {
        assertEquals("{name=attribute1, familyName=attribute2, birthday=attribute3, id=attribute4}", myUsers.get(0).toString());
    }

}