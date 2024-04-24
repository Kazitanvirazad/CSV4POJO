package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

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
