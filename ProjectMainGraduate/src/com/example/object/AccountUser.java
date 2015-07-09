package com.example.object;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account, password, address, phone, email, fullName, id;
	private int countMessage, countMessageAdmin;
	private ArrayList<String> arrayMessage;

	public AccountUser() {
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCountMessage() {
		return countMessage;
	}

	public void setCountMessage(int countMessage) {
		this.countMessage = countMessage;
	}

	public ArrayList<String> getArrayMessage() {
		return arrayMessage;
	}

	public void setArrayMessage(ArrayList<String> arrayMessage) {
		this.arrayMessage = arrayMessage;
	}

	public int getCountMessageAdmin() {
		return countMessageAdmin;
	}

	public void setCountMessageAdmin(int countMessageAdmin) {
		this.countMessageAdmin = countMessageAdmin;
	}

}
