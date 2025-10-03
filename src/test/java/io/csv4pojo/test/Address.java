package io.csv4pojo.test;

import io.csv4pojo.annotation.FieldType;
import io.csv4pojo.annotation.Type;

public class Address {
    @FieldType(dataType = Type.STRING)
    private String city;
    @FieldType(dataType = Type.STRING)
    private String country;
    @FieldType(dataType = Type.STRING)
    private String state;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
