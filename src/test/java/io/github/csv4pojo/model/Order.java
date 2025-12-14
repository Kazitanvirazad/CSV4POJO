package io.github.csv4pojo.model;

import io.github.csv4pojo.annotation.FieldType;
import io.github.csv4pojo.annotation.FieldType.Type;

import java.io.Serializable;
import java.util.Arrays;

public class Order implements Serializable {
    private static final long serialVersionUID = 7539434769320340126L;
    @FieldType(dataType = Type.STRING, csvColumnName = "Order Id")
    private String orderId;
    @FieldType(dataType = Type.CLASSTYPE)
    private Customer customer;
    @FieldType(dataType = Type.FLOAT, csvColumnName = "Line Item Price")
    private Float price;
    @FieldType(dataType = Type.FLOAT, csvColumnName = "Discount")
    private float discount;
    @FieldType(dataType = Type.FLOAT, csvColumnName = "Selling Price")
    private float sellingPrice;
    @FieldType(dataType = Type.STRING, csvColumnName = "Order Date")
    private String orderDate;
    @FieldType(dataType = Type.CLASSTYPE)
    private LineItem lineItem;
    @FieldType(dataType = Type.STRING_ARRAY, csvColumnName = "Custom Messages")
    private String[] customMessages;

    public Order() {
    }

    public Order(Customer customer, String[] customMessages, float discount, LineItem lineItem, String orderDate, String orderId, Float price, float sellingPrice) {
        this.customer = customer;
        this.customMessages = customMessages;
        this.discount = discount;
        this.lineItem = lineItem;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.price = price;
        this.sellingPrice = sellingPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String[] getCustomMessages() {
        return customMessages;
    }

    public void setCustomMessages(String[] customMessages) {
        this.customMessages = customMessages;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public LineItem getLineItem() {
        return lineItem;
    }

    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", orderId='" + orderId + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", sellingPrice=" + sellingPrice +
                ", orderDate='" + orderDate + '\'' +
                ", lineItem=" + lineItem +
                ", customMessages=" + Arrays.toString(customMessages) +
                '}';
    }
}
