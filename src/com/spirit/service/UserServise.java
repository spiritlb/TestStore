package com.spirit.service;


import com.spirit.domain.User;

public interface UserServise {

	void regist(User user) throws Exception;

	User active(String code) throws Exception;

	User login(String username, String password) throws Exception;

}
