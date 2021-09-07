package com.example.petsmarthome;

public class CollarDetails {
    public int Pet_ID;
    public double Neck_Width;
    public String Collar_Material;
    public String Collar_Color;
    public double Price;

    public CollarDetails() {
    }

    public CollarDetails(int pet_ID, double neck_Width, String collar_Material, String collar_Color, double price) {
        Pet_ID = pet_ID;
        Neck_Width = neck_Width;
        Collar_Material = collar_Material;
        Collar_Color = collar_Color;
        Price = price;
    }
}
