package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Tyre {

	@FieldType(dataType = Type.STRING, csvColumnName = "tyre_brandName")
	private String tyreBrand;

	private int tyreWidth;

	private int tyreBreadth;

	@FieldType(dataType = Type.INTEGER, csvColumnName = "tyre_selfLife")
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
