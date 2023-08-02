package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Human {
	@FieldType(Type.STRING)
	private String name;

	@FieldType(Type.STRING)
	private String gender;

	@FieldType(Type.CLASS)
	private Student student;

	@FieldType(Type.INTEGER)
	private int age;

	public Human() {
		super();
	}

	public Human(String name, String gender, Student student, int age) {
		super();
		this.name = name;
		this.gender = gender;
		this.student = student;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Human [name=" + name + ", gender=" + gender + ", student=" + student + ", age=" + age + "]";
	}

}
