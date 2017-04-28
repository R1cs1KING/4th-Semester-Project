package com.example.android.PickFood;

/**
 * Created by Aurelijus on 28/04/2017.
 */

public  class FoodItem {
    public String Type;
    public String Name;
    public String Location;
    public String Description;

    public FoodItem(String type, String name, String location, String description)
    {
        this.Type = type;
        this.Name = name;
        this.Location = location;
        this.Description = description;

    }
}
