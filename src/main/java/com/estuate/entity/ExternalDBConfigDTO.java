package com.estuate.entity;

public class ExternalDBConfigDTO {


	private String host;
	private int port;
	private String userName;
	private String password;

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "ExternalDBConfigDTO [host=" + host + ", port=" + port + ", userName=" + userName + ", password="
				+ password + "]";
	}	 
	
	

}
