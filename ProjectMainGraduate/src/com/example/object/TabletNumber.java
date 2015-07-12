package com.example.object;

import com.parse.ParseFile;

public class TabletNumber {
	private ParseFile image;
	private String color;
	private int number, numberSold;
	private String idDetails;

	public ParseFile getImage() {
		return image;
	}

	public void setImage(ParseFile image) {
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getIdDetails() {
		return idDetails;
	}

	public void setIdDetails(String idDetails) {
		this.idDetails = idDetails;
	}

	public int getNumberSold() {
		return numberSold;
	}

	public void setNumberSold(int numberSold) {
		this.numberSold = numberSold;
	}
}
