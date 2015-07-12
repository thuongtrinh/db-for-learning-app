package com.example.object;

import com.parse.ParseFile;

public class Gift {

	private ParseFile image;
	private String label;
	private String id;
	private String content;
//	private ArrayList<String> arrayIdPromotions;


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public ParseFile getImage() {
		return image;
	}

	public void setImage(ParseFile image) {
		this.image = image;
	}


}
