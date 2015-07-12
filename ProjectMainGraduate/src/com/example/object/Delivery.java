package com.example.object;

import java.io.Serializable;
import java.util.Date;

public class Delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Date time;
	private int soluong;
	private boolean stateDelivery;
	private AccountUser user;
	private DetailPhone phone;
	private DetailTablet tablet;
	private DetailAccessory accessory;
	private String color, type, addressDelivery;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getSoluong() {
		return soluong;
	}

	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}

	public boolean isStateDelivery() {
		return stateDelivery;
	}

	public void setStateDelivery(boolean stateDelivery) {
		this.stateDelivery = stateDelivery;
	}

	public AccountUser getUser() {
		return user;
	}

	public void setUser(AccountUser user) {
		this.user = user;
	}

	public DetailPhone getPhone() {
		return phone;
	}

	public void setPhone(DetailPhone phone) {
		this.phone = phone;
	}

	public DetailTablet getTablet() {
		return tablet;
	}

	public void setTablet(DetailTablet tablet) {
		this.tablet = tablet;
	}

	public DetailAccessory getAccessory() {
		return accessory;
	}

	public void setAccessory(DetailAccessory accessory) {
		this.accessory = accessory;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAddressDelivery() {
		return addressDelivery;
	}

	public void setAddressDelivery(String addressDelivery) {
		this.addressDelivery = addressDelivery;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
