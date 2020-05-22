package com.example.demo;

import java.util.Map;

public class User
{
    private String name;
    private String familyName;
    private String birthday;
    private String id;
    public User(String name, String familyName, String birthday, String id)
    {
        this.name = name;
        this.familyName = familyName;
        this.birthday = birthday;
        this.id=id;
    }
    public User() {}

    public User(Map<String, String> user) {
       this.name=user.get("name");
       this.familyName = user.get("familyName");
       this.birthday =user.get("birthday");
       this.id=user.get("id");
    }

    public String getName()
    {
        return this.name;
    }
    public String getFamilyName()
    {
        return this.familyName;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getId(){
        return id;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id){this.id=id;}

    @Override
    public String toString()
    {
        return "{\"name\":" +"\""+ this.getName()+"\""+",\"familyName\":"+"\""+ this.getFamilyName() +"\""+ ",\"birthday\":"+"\""+ this.getBirthday() +"\""+ ",\"id\":" +"\""+this.getId()+ "\""+"}";
    }
}
