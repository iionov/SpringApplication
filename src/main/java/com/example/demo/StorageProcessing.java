package com.example.demo;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.*;
import java.util.Map;

import static com.example.demo.CommandsProcessing.*;

/**
 * Class for working with storage (database).
 */
public class StorageProcessing {
    /**
     * Before each start of the program this method updates data from a file.
     */
    public void getUsersFromFile(String path) {
        JsonElement js = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
            if (randomAccessFile.length() > 0) {
                try {
                    js = new JsonParser().parse(new FileReader(path));
                    JsonObject jo = (JsonObject) js;
                    myUsersJsonArray = (JsonArray) jo.get("Users");
                    for (int i = 0; i < myUsersJsonArray.size(); i++) {
                        JsonElement elementFromJsonArray = myUsersJsonArray.get(i);
                        String founderJson = elementFromJsonArray.toString();
                        transformationJsonElementToMap(founderJson);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                randomAccessFile.writeBytes("{\"Users\":[]}");
                randomAccessFile.close();
                getUsersFromFile(path);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method adds new users to the file.
     *
     * @param string information about new user
     */
    public void writeToJsonNewUser(String string, String path) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
            long pos = randomAccessFile.length();
            if (randomAccessFile.length() > 0) {
                while (randomAccessFile.length() > 0) {
                    pos--;
                    randomAccessFile.seek(pos);
                    if (randomAccessFile.readByte() == ']') {
                        randomAccessFile.seek(pos);
                        break;
                    }
                }
                if (myUsers.size() > 1) {
                    randomAccessFile.writeBytes("," + string + "]}");
                } else randomAccessFile.writeBytes(string + "]}");
                randomAccessFile.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method converts a string to a map.
     *
     * @param jsonString string, that want ro convert.
     */
    public void transformationJsonElementToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
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

    /**
     * This method deletes data from the file.
     *
     * @throws FileNotFoundException signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    public void deleteUserInFile(String path) throws FileNotFoundException {
        if (myUsers.size() == 0) {
            PrintWriter writer = new PrintWriter(path);
            writer.print("");
            writer.close();
        } else {
            writeToFileAfterUpdatingUser(path);
        }
    }

    /**
     * This method updates data in the file.
     */
    public void writeToFileAfterUpdatingUser(String path) {
        try {
            PrintWriter writer = new PrintWriter(new File(path));
            Gson gson = new Gson();
            writer.print("{\"Users\":[");
            if (myUsers.size() == 1)
                writer.print(gson.toJson(myUsers.get(0)));
            else {
                writer.print(gson.toJson(myUsers.get(0)));
                for (int j = 1; j < myUsers.size(); j++) {
                    writer.print("," + gson.toJson(myUsers.get(j)));
                }
            }
            writer.print("]}");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
