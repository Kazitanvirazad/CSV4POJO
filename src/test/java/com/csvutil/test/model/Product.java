package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Product {
	@FieldType(Type.STRING)
	private String name;

	@FieldType(Type.STRING)
	private String color;

	@FieldType(Type.INTEGER)
	private int inventory;

	@FieldType(Type.INTEGER)
	private int price;

	@FieldType(Type.STRING)
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
