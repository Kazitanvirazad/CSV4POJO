package io.github.csv4pojo.model;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.FieldType.Type;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = -9209689376483305449L;
    @FieldType(dataType = Type.STRING, csvColumnName = "Street")
    private String street;
    @FieldType(dataType = Type.INTEGER, csvColumnName = "Pin")
    private Integer pin;
    @FieldType(dataType = Type.STRING, csvColumnName = "City")
    private String city;
    @FieldType(dataType = Type.STRING, csvColumnName = "State")
    private String state;
    private String lastModificationDate;

    public Address() {
    }

    public Address(String city, String lastModificationDate, Integer pin, String state, String street) {
        this.city = city;
        this.lastModificationDate = lastModificationDate;
        this.pin = pin;
        this.state = state;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", pin=" + pin +
                ", state='" + state + '\'' +
                ", lastModificationDate='" + lastModificationDate + '\'' +
                '}';
    }
}
