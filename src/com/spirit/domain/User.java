package com.spirit.domain;

import java.util.Date;

public class User {
	/*
	 *	'uid' varchar(32) not null,
		'username' varchar(20) dafault null,
		'password' varchar(20) dafault null,
		
		'name' varchar(20) default null,
		'email' varchar(30) default null,
		'telephone' varchar(20) default null,
		
		'birthday' date default null,
		'sex' varchar(10) default null,
		'state' int(11) default null,
		'code' varcher(64) default null, 
	 */
	private String uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private String telephone;
	private String birthday;
	private String sex;
	private Integer state;
	private String code;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", name=" + name + ", email=" + email
				+ ", telephone=" + telephone + ", birthday=" + birthday
				+ ", sex=" + sex + ", state=" + state + ", code=" + code + "]";
	}
	
	
	
}
