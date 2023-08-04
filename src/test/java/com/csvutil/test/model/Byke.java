package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Byke {

	@FieldType(Type.STRING)
	private String brandName;

	@FieldType(Type.STRING)
	private String modelName;

	@FieldType(Type.STRING)
	private String color;

	@FieldType(Type.CLASSTYPE)
	private Tyre tyre;

	@FieldType(Type.CLASSTYPE)
	private Engine engine;

	@FieldType(Type.INTEGER)
	private int kerbWeight;

	private float price;

	public Byke(String brandName, String modelName, String color, Tyre tyre, Engine engine, int kerbWeight,
			float price) {
		this();
		this.brandName = brandName;
		this.modelName = modelName;
		this.color = color;
		this.tyre = tyre;
		this.engine = engine;
		this.kerbWeight = kerbWeight;
		this.price = price;
	}

	public Byke() {
		super();
	}

	@Override
	public String toString() {
		return "Byke [brandName=" + brandName + ", modelName=" + modelName + ", color=" + color + ", tyre=" + tyre
				+ ", engine=" + engine + ", kerbWeight=" + kerbWeight + ", price=" + price + "]";
	}

}
