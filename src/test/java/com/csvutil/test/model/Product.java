package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Product {
	@FieldType(dataType = Type.STRING, csvColumnName = "product_name")
	private String name;

	@FieldType(dataType = Type.STRING, csvColumnName = "product_color")
	private String color;

	@FieldType(dataType = Type.INTEGER)
	private int inventory;

	@FieldType(dataType = Type.INTEGER, csvColumnName = "product_price")
	private int price;

	@FieldType(dataType = Type.STRING, csvColumnName = "product_category")
	private String category;

	public Product(String name, String color, int inventory, int price, String category) {
		this();
		this.name = name;
		this.color = color;
		this.inventory = inventory;
		this.price = price;
		this.category = category;
	}

	public Product() {
		super();
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", color=" + color + ", inventory=" + inventory + ", price=" + price
				+ ", category=" + category + "]";
	}
}
