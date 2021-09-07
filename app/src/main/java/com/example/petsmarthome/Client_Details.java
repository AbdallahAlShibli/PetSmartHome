package com.example.petsmarthome;

public class Client_Details {
    public String ID ;
    public String NAME;
    public String GENDER;
    public String AGE;
    public String ADDRESS;
    public String EMAIL;
    public String PHONE;
    public String PASS;

    public Client_Details() {
    }

    public Client_Details(String c_ID, String c_NAME, String c_GENDER, String c_AGE, String c_ADDRESS, String c_EMAIL, String c_PHONE, String PASSWORD) {
        ID = c_ID;
        NAME = c_NAME;
        GENDER = c_GENDER;
        AGE = c_AGE;
        ADDRESS = c_ADDRESS;
        EMAIL = c_EMAIL;
        PHONE = c_PHONE;
        PASS= PASSWORD;
    }

}
