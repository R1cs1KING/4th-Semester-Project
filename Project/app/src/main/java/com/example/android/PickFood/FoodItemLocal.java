package com.example.android.PickFood;

/**
 * Created by Aurelijus on 28/04/2017.
 */

public  class FoodItemLocal {
    public static String Type;
    public static String Name;
    public static String Location;
    public static String Description;
    public static String url;
    public static String Owner;

    public FoodItemLocal()
    {

    }

    public FoodItemLocal(String type, String name, String location, String description, String url, String owner)
    {
        this.Type = type;
        this.Name = name;
        this.Location = location;
        this.Description = description;
        this.url = url;
        this.Owner = owner;
    }


}