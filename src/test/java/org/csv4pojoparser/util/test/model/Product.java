package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Objects;

public class Product {
    @FieldType(dataType = Type.STRING, csvColumnName = "product_name")
    private String name;

    @FieldType(dataType = Type.STRING, csvColumnName = "product_color")
    private String color;

    @FieldType(dataType = Type.CLASSTYPE)
    private Inventory inventory;

    @FieldType(dataType = Type.FLOAT)
    private Float price;

    private Float taxRate;

    @FieldType(dataType = Type.CLASSTYPE)
    private Category category;

    public Product() {
    }

    public Product(String name, String color, Inventory inventory, Float price, Float taxRate, Category category) {
        this.name = name;
        this.color = color;
        this.inventory = inventory;
        this.price = price;
        this.taxRate = taxRate;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", inventory=" + inventory +
                ", price=" + price +
                ", taxRate=" + taxRate +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(color, product.color) && Objects.equals(inventory, product.inventory) && Objects.equals(price, product.price) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, inventory, price, category);
    }
}
