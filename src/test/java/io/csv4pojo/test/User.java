package io.csv4pojo.test;

import io.csv4pojo.annotation.FieldType;
import io.csv4pojo.annotation.Type;

public class User {
    @FieldType(dataType = Type.STRING, csvColumnName = "first_name")
    private String firstName;
    @FieldType(dataType = Type.STRING, csvColumnName = "last_name")
    private String lastName;
    @FieldType(dataType = Type.STRING)
    private String email;
    @FieldType(dataType = Type.STRING)
    private String gender;
    @FieldType(dataType = Type.STRING)
    private String phone;
    @FieldType(dataType = Type.CLASSTYPE)
    private Address address;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}
