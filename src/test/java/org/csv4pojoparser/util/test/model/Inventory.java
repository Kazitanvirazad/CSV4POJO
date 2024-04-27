package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Arrays;

public class Inventory {
    @FieldType(dataType = Type.INTEGER, csvColumnName = "inventory_id")
    private int inventoryId;

    @FieldType(dataType = Type.STRING)
    private String location;

    @FieldType(dataType = Type.INTEGER, csvColumnName = "items_count")
    private int itemsCount;

    @FieldType(dataType = Type.INTEGER_ARRAY, csvColumnName = "skus")
    private Integer[] skus;

    public Inventory() {
    }

    public Inventory(int inventoryId, String location, int itemsCount, Integer[] skus) {
        this.inventoryId = inventoryId;
        this.location = location;
        this.itemsCount = itemsCount;
        this.skus = skus;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", location='" + location + '\'' +
                ", itemsCount=" + itemsCount +
                ", skus=" + Arrays.toString(skus) +
                '}';
    }
}
