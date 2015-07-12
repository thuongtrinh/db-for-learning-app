package com.example.object;

import com.parse.ParseFile;

public class TypePhone {
	private String name, id;
	private ParseFile avatar;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public ParseFile getAvatar() {
		return avatar;
	}

	public void setAvatar(ParseFile avatar) {
		this.avatar = avatar;
	}

}
