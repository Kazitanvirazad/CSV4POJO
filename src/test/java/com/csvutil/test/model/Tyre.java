package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Tyre {

	@FieldType(Type.STRING)
	private String tyreBrand;

	private int tyreWidth;

	private int tyreBreadth;

	@FieldType(Type.INTEGER)
	private int selfLife;

	public Tyre(String tyreBrand, int tyreWidth, int tyreBreadth, int selfLife) {
		super();
		this.tyreBrand = tyreBrand;
		this.tyreWidth = tyreWidth;
		this.tyreBreadth = tyreBreadth;
		this.selfLife = selfLife;
	}

	public Tyre() {
		super();
	}

	@Override
	public String toString() {
		return "Tyre [tyreBrand=" + tyreBrand + ", tyreWidth=" + tyreWidth + ", tyreBreadth=" + tyreBreadth
				+ ", selfLife=" + selfLife + "]";
	}

}
