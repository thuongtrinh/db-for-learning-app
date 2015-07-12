package com.example.object;

import com.parse.ParseFile;

public class TypeAccessory {

	private String nameRight, nameLeft, idLeft, idRight;
	private ParseFile imageRight, imageLeft;
	private String ratingLeft, ratingRight;
	private int ratingNumberLeft, ratingNumberRight;

	public String getRatingLeft() {
		return ratingLeft;
	}

	public void setRatingLeft(String ratingLeft) {
		this.ratingLeft = ratingLeft;
	}

	public String getRatingRight() {
		return ratingRight;
	}

	public void setRatingRight(String ratingRight) {
		this.ratingRight = ratingRight;
	}

	public int getRatingNumberLeft() {
		return ratingNumberLeft;
	}

	public void setRatingNumberLeft(int ratingNumberLeft) {
		this.ratingNumberLeft = ratingNumberLeft;
	}

	public int getRatingNumberRight() {
		return ratingNumberRight;
	}

	public void setRatingNumberRight(int ratingNumberRight) {
		this.ratingNumberRight = ratingNumberRight;
	}

	public String getNameRight() {
		return nameRight;
	}

	public void setNameRight(String nameRight) {
		this.nameRight = nameRight;
	}

	public String getNameLeft() {
		return nameLeft;
	}

	public void setNameLeft(String nameLeft) {
		this.nameLeft = nameLeft;
	}

	public ParseFile getImageRight() {
		return imageRight;
	}

	public void setImageRight(ParseFile imageRight) {
		this.imageRight = imageRight;
	}

	public ParseFile getImageLeft() {
		return imageLeft;
	}

	public void setImageLeft(ParseFile imageLeft) {
		this.imageLeft = imageLeft;
	}

	public String getIdLeft() {
		return idLeft;
	}

	public void setIdLeft(String idLeft) {
		this.idLeft = idLeft;
	}

	public String getIdRight() {
		return idRight;
	}

	public void setIdRight(String idRight) {
		this.idRight = idRight;
	}

}
