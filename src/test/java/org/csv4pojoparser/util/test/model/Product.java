package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

public class Product {
    @FieldType(dataType = Type.STRING, csvColumnName = "product_name")
    private String name;

    @FieldType(dataType = Type.STRING, csvColumnName = "product_color")
    private String color;

    @FieldType(dataType = Type.CLASSTYPE)
    private Inventory inventory;

    @FieldType(dataType = Type.FLOAT)
    private float price;

    @FieldType(dataType = Type.CLASSTYPE)
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", inventory=" + inventory +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
