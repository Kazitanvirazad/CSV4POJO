package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Animal {
	@FieldType(Type.STRING)
	public String name;
	@FieldType(Type.STRING)
	public String foodType;
	@FieldType(Type.STRING)
	public String type;

	public Animal() {
		super();
	}
}
