package com.csvutil.test.model;

import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Student {
	@FieldType(Type.STRING)
	private String collegeName;

	@FieldType(Type.STRING)
	private String courseName;

	public Student() {
		super();
	}

	public Student(String collegeName, String courseName) {
		this();
		this.collegeName = collegeName;
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "Student [collegeName=" + collegeName + ", courseName=" + courseName + "]";
	}

}
