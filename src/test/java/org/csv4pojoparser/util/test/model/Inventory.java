package org.csv4pojoparser.util.test.model;

import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

import java.util.Arrays;
import java.util.Objects;

public class Inventory {
    @FieldType(dataType = Type.INTEGER, csvColumnName = "inventory_id")
    private Integer inventoryId;

    @FieldType(dataType = Type.STRING)
    private String location;

    @FieldType(dataType = Type.INTEGER, csvColumnName = "items_count")
    private Integer itemsCount;

    @FieldType(dataType = Type.INTEGER_ARRAY, csvColumnName = "skus")
    private Integer[] skus;

    public Inventory() {
    }

    public Inventory(Integer inventoryId, String location, Integer itemsCount, Integer[] skus) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(inventoryId, inventory.inventoryId) && Objects.equals(location, inventory.location) && Objects.equals(itemsCount, inventory.itemsCount) && Objects.deepEquals(skus, inventory.skus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, location, itemsCount, Arrays.hashCode(skus));
    }
}
