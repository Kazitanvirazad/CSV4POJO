package io.github.csv4pojo.model;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.FieldType.Type;

import java.io.Serializable;
import java.util.Arrays;

public class LineItem implements Serializable {
    private static final long serialVersionUID = -6659657352890779842L;
    @FieldType(dataType = Type.FLOAT, csvColumnName = "Price")
    private Float price;
    @FieldType(dataType = Type.STRING, csvColumnName = "SKU")
    private String sku;
    @FieldType(dataType = Type.STRING, csvColumnName = "Item Name")
    private String name;
    @FieldType(dataType = Type.INTEGER, csvColumnName = "Quantity")
    private int quantity;
    @FieldType(dataType = Type.INTEGER_ARRAY, csvColumnName = "Stock Inventory Ids")
    private Integer[] stockInventoryIds;

    public LineItem() {
    }

    public LineItem(String name, Float price, int quantity, String sku, Integer[] stockInventoryIds) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.stockInventoryIds = stockInventoryIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer[] getStockInventoryIds() {
        return stockInventoryIds;
    }

    public void setStockInventoryIds(Integer[] stockInventoryIds) {
        this.stockInventoryIds = stockInventoryIds;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                ", quantity=" + quantity +
                ", stockInventoryIds=" + Arrays.toString(stockInventoryIds) +
                '}';
    }
}
