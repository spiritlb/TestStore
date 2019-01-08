package com.spirit.test;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.spirit.dao.impl.UserDaoImpl;
import com.spirit.domain.User;
import com.spirit.utils.BaseDao3;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		UserDaoImpl ud = new UserDaoImpl();
		User u = ud.getByCode("4DA5351793634432983584EF7DF116E5");
		System.out.println(u.getCode());
	}
}
