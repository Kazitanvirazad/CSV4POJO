package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

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
