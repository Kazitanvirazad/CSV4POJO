package com.csv4pojo.util.test;

import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Engine {

	@FieldType(dataType = Type.FLOAT)
	private float cc;

	@FieldType(dataType = Type.FLOAT, csvColumnName = "engine_horsepower")
	private float bhp;

	@FieldType(dataType = Type.INTEGER, csvColumnName = "engine_cylinders")
	private int cylinders;

	public Engine() {
		super();
	}

	public Engine(float cc, float bhp, int cylinders) {
		this();
		this.cc = cc;
		this.bhp = bhp;
		this.cylinders = cylinders;
	}

	@Override
	public String toString() {
		return "Engine [cc=" + cc + ", bhp=" + bhp + ", cylinders=" + cylinders + "]";
	}

}
