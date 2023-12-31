package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Vehicle {

	@FieldType(dataType = Type.STRING, csvColumnName = "vahicle_name")
	private String name;

	@FieldType(dataType = Type.BOOLEAN)
	private boolean isElectric;

	@FieldType(dataType = Type.CLASSTYPE)
	private Byke byke;

	public Vehicle(String name, boolean isElectric, Byke byke) {
		this();
		this.name = name;
		this.isElectric = isElectric;
		this.byke = byke;
	}

	public Vehicle() {
		super();
	}

	@Override
	public String toString() {
		return "Vehicle [name=" + name + ", isElectric=" + isElectric + ", byke=" + byke + "]";
	}

}
