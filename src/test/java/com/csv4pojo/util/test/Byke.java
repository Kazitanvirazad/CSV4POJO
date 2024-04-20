package com.csv4pojo.util.test;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Byke {

	@FieldType(dataType = Type.STRING, csvColumnName = "byke_brandName")
	private String brandName;

	@FieldType(dataType = Type.STRING, csvColumnName = "byke_modelName")
	private String modelName;

	@FieldType(dataType = Type.STRING, csvColumnName = "byke_color")
	private String color;

	@FieldType(dataType = Type.CLASSTYPE)
	private Tyre tyre;

	@FieldType(dataType = Type.CLASSTYPE)
	private Engine engine;

	@FieldType(dataType = Type.INT)
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
