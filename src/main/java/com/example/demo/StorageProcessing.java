package com.example.demo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import java.io.*;
import java.util.Map;

import static com.example.demo.GreetingController.myUsers;
import static com.example.demo.GreetingController.myusers;

public class StorageProcessing {

    public static void getUsersFromFile()  {
        JsonElement js= null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("src\\main\\resources\\templates\\Users.json", "rw");
            if (randomAccessFile.length()>0){
                try {
                    js = new JsonParser().parse(new FileReader("src\\main\\resources\\templates\\Users.json"));
                    JsonObject jo =(JsonObject) js;
                    myusers=(JsonArray) jo.get("Users");
                    String my0=myusers.get(0).toString();
                    for (int i=0;i<myusers.size();i++){
                        JsonElement elementFromJsonArray=myusers.get(i);
                        String founderJson=elementFromJsonArray.toString();
                        transformationJsonElementToMap(founderJson);
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                randomAccessFile.writeBytes("{\"Users\":[]}");
                randomAccessFile.close();
                getUsersFromFile();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToJsonNewUser(String string){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("src\\main\\resources\\templates\\Users.json", "rw");
            long pos = randomAccessFile.length();
            if (randomAccessFile.length()>0){
                while (randomAccessFile.length() > 0) {
                    pos--;
                    randomAccessFile.seek(pos);
                    if (randomAccessFile.readByte() == ']') {
                        randomAccessFile.seek(pos);
                        break;
                    }
                }
                if (myUsers.size()>1){
                    randomAccessFile.writeBytes("," + string + "]}");
                }
                else randomAccessFile.writeBytes(string + "]}");
                randomAccessFile.close();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void transformationJsonElementToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            Map<String, String> jsonInMap = mapper.readValue(jsonString,
                    new TypeReference<Map<String,String>>() {});
            myUsers.add(jsonInMap);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserInFile(String id) throws FileNotFoundException {
        if (myUsers.size()==0){
            PrintWriter writer = new PrintWriter("src\\main\\resources\\templates\\Users.json");
            writer.print("");
            writer.close();
        }
        else {
            writeToFileAfterUpdatingUser();
        }
    }
    public static void writeToFileAfterUpdatingUser(){
        try {
            PrintWriter writer=new PrintWriter(new File("src\\main\\resources\\templates\\Users.json"));
            Gson gson=new Gson();
            writer.print("{\"Users\":[");
                if (myUsers.size()==1)
                writer.print(gson.toJson(myUsers.get(0)));
                else{
                    writer.print(gson.toJson(myUsers.get(0)));
                    for (int j=1; j<myUsers.size();j++){
                        writer.print(","+gson.toJson(myUsers.get(j)));
                    }
                }
            writer.print("]}");
            writer.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
