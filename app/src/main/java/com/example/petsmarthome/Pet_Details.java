package com.example.petsmarthome;

public class Pet_Details {
    public String C_ID;
    public String PET_ID;
    public String PET_NAME;
    public String PET_COLOR;
    public String PET_TYPE;

    public Pet_Details() {
    }

    public Pet_Details(String c_ID, String PET_ID, String PET_NAME, String PET_COLOR, String PET_TYPE) {
        C_ID = c_ID;
        this.PET_ID = PET_ID;
        this.PET_NAME = PET_NAME;
        this.PET_COLOR = PET_COLOR;
        this.PET_TYPE = PET_TYPE;
    }

}
