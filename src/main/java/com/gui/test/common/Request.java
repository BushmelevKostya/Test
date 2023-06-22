package com.gui.test.common;

import com.gui.test.common.product.Product;

import java.io.Serializable;

public class Request implements Serializable {
	private String AccessToken;
	private String RefreshToken;
	private String command = null;
	private String value = null;
	private Product product;
	private String RequestType;
	private String login = null;
	private String name = null;
	private String password = null;
	

	public Request(String command, String value) {
		this.command = command;
		this.value = value;
	}
	
	public Request(String login, String name, String password) {
		this.login = login;
		this.name = name;
		this.password = password;
	}
	
	public String getAccessToken() {
		return AccessToken;
	}
	
	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return RefreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getValue() {
		return value;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getRequestType() {
		return RequestType;
	}
	
	public void setRequestType(String requestType) {
		RequestType = requestType;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
