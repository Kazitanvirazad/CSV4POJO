package com.csv4pojo.util.test.model;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Computer {

    @FieldType(dataType = Type.STRING, csvColumnName = "brandname")
    private String brandName;

    @FieldType(dataType = Type.CLASSTYPE)
    private Ram ram;
    @FieldType(dataType = Type.INT, csvColumnName = "price")
    private int price;

    @Override
    public String toString() {
        return "Computer{" +
                "brandName='" + brandName + '\'' +
                ", ram=" + ram +
                ", price=" + price +
                '}';
    }
}
