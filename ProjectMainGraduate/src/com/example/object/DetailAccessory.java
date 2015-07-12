package com.example.object;

import com.parse.ParseFile;

public class DetailAccessory {

	/**
	 * 
	 */
	private ParseFile avatar;
	private String name;
	private String price;
	private String condition;
	private String id;
	private boolean saleOff;
	private int percentPromotion, SumAccessorySold, SumAccessory;
	//
	private String guarantee, state, inforDetail;
	private ParseFile image1, image2, image3, image4, image5;
	private ParseFile imageRepresent;

	public int getSumAccessorySold() {
		return SumAccessorySold;
	}

	public void setSumAccessorySold(int sumAccessorySold) {
		SumAccessorySold = sumAccessorySold;
	}

	public int getSumAccessory() {
		return SumAccessory;
	}

	public void setSumAccessory(int sumAccessory) {
		SumAccessory = sumAccessory;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInforDetail() {
		return inforDetail;
	}

	public void setInforDetail(String inforDetail) {
		this.inforDetail = inforDetail;
	}

	public ParseFile getImage1() {
		return image1;
	}

	public void setImage1(ParseFile image1) {
		this.image1 = image1;
	}

	public ParseFile getImage2() {
		return image2;
	}

	public void setImage2(ParseFile image2) {
		this.image2 = image2;
	}

	public ParseFile getImage3() {
		return image3;
	}

	public void setImage3(ParseFile image3) {
		this.image3 = image3;
	}

	public ParseFile getImage4() {
		return image4;
	}

	public void setImage4(ParseFile image4) {
		this.image4 = image4;
	}

	public ParseFile getImage5() {
		return image5;
	}

	public void setImage5(ParseFile image5) {
		this.image5 = image5;
	}

	public ParseFile getAvatar() {
		return avatar;
	}

	public void setAvatar(ParseFile avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getConditon() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ParseFile getImageRepresent() {
		return imageRepresent;
	}

	public void setImageRepresent(ParseFile imageRepresent) {
		this.imageRepresent = imageRepresent;
	}

	public boolean isSaleOff() {
		return saleOff;
	}

	public void setSaleOff(boolean saleOff) {
		this.saleOff = saleOff;
	}

	public int getPercentPromotion() {
		return percentPromotion;
	}

	public void setPercentPromotion(int percentPromotion) {
		this.percentPromotion = percentPromotion;
	}

}
