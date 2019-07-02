package com.developer.wise4rmgod.salvichospitalapp.model;

public class PatientClass {
    String sex;
    String fullname;
    String age;
    String email;

    public PatientClass() {
    }

    public PatientClass(String sex, String fullname, String age, String email) {
        this.sex = sex;
        this.fullname = fullname;
        this.age = age;
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
