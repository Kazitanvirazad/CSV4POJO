package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Engine {

	@FieldType(Type.FLOAT)
	private float cc;

	@FieldType(Type.FLOAT)
	private float bhp;

	@FieldType(Type.INTEGER)
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
