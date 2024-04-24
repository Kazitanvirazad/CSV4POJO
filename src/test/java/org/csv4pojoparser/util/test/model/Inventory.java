package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

public class Inventory {
    @FieldType(dataType = Type.INT, csvColumnName = "inventory_id")
    private int inventoryId;

    @FieldType(dataType = Type.STRING)
    private String location;

    @FieldType(dataType = Type.INT, csvColumnName = "items_count")
    private int itemsCount;

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", location='" + location + '\'' +
                ", itemsCount=" + itemsCount +
                '}';
    }
}
