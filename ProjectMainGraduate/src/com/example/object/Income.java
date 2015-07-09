package com.example.object;

public class Income {

	private String Time, SumMoney;
	private String productLabel, type;
	private String price, id;
	private int soluong;

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getSoluong() {
		return soluong;
	}

	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the sumMoney
	 */
	public String getSumMoney() {
		return SumMoney;
	}

	/**
	 * @param sumMoney the sumMoney to set
	 */
	public void setSumMoney(String sumMoney) {
		SumMoney = sumMoney;
	}

}
