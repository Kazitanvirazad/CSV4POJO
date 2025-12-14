package io.github.csv4pojo.model;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.FieldType.Type;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = -4024215294912918171L;
    @FieldType(dataType = Type.STRING, csvColumnName = "Name")
    private String name;
    @FieldType(dataType = Type.STRING, csvColumnName = "Creation Date")
    private String creationDate;
    @FieldType(dataType = FieldType.Type.CLASSTYPE)
    private Address address;

    public Customer() {
    }

    public Customer(Address address, String creationDate, String name) {
        this.address = address;
        this.creationDate = creationDate;
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                ", name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
