package com.developer.wise4rmgod.salvichospitalapp.model;

public class PatientClass {
    String id;
    String fullname;
    String age;
    String gender;

    public PatientClass() {
    }

    public PatientClass(String id, String fullname, String age, String gender) {
        this.id = id;
        this.fullname = fullname;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
