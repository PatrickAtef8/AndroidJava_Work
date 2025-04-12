package com.example.recyclerviewcars;

public class Car {
    private String name;
    private String type;
    private int imageResource;

    public Car(String name, String type, int imageResource) {
        this.name = name;
        this.type = type;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getImageResource() {
        return imageResource;
    }
}