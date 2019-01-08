package com.spirit.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Spring;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.spirit.dao.UserDao;
import com.spirit.domain.User;
import com.spirit.utils.BaseDao3;
import com.spirit.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{
	/**
	 * 用户注册
	 * @throws SQLException 
	 */
	public void save(User user) throws SQLException {
		//QueryRunner qr =  new QueryRunner(DataSourceUtils.getDataSource());
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

		System.out.println("User对象"+user);
		BaseDao3.changePos("insert into user values(?,?,?,?,?,?,?,?,?,?)", user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode());
	}

	/**
	 * 通过激活码获取用户
	 */
	public User getByCode(String code) throws Exception {
		QueryRunner qr =  new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		return (User) qr.query(sql, new BeanListHandler<>(User.class),code).get(0);
	}

	/**
	 * 更新用户
	 */
	public void update(User user) throws Exception {
		QueryRunner qr =  new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set password = ?,state = ?,code = ? where uid = ?";
		qr.update(sql,user.getPassword(),user.getState(),user.getCode(),user.getUid());
	}

	//用户登录
	public User getByUsernameAndPwd(String username, String password)
			throws Exception {
		QueryRunner qr =  new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class),username,password);
	}


}
