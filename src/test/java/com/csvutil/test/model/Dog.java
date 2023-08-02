package com.csvutil.test.model;

public class Dog {
	private String dogName;

	private String dogBreed;

	public Dog() {
		super();
	}

	public Dog(String dogName, String dogBreed) {
		this();
		this.dogName = dogName;
		this.dogBreed = dogBreed;
	}

	@Override
	public String toString() {
		return "Dog [dogName=" + dogName + ", dogBreed=" + dogBreed + "]";
	}

}
