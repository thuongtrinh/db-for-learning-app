package com.example.object;

import com.parse.ParseFile;

public class TypeTablet {

	private String name;
	private ParseFile image;
	private String id;
	private String rating;
	private int ratingNumber;
	
	public TypeTablet(){
		super();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public ParseFile getImage() {
		return image;
	}

	public void setImage(ParseFile image) {
		this.image = image;
	}


	public String getId() {
		return id;
	} 

	public void setId(String id) {
		this.id = id;
	}


	 


	public int getRatingNumber() {
		return ratingNumber;
	}


	public void setRatingNumber(int ratingNumber) {
		this.ratingNumber = ratingNumber;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}

	


}
