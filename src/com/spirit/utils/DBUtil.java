package com.spirit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/*
 * 获取数据库连接
 * 通过读取db.properties 文件 建立数据库连接
 */
public class DBUtil {
	public static String driver=null;
	public static String url=null;
	public static String uname=null;
	public static String pwd=null;
	static{
		try {
			//获取properties文件 通过键给属性赋值
			Properties properties=new Properties();
			//加载 db.properties
			properties.load(DBUtil.class.getResourceAsStream("/db.properties"));
			//获取 对应 db属性
			driver=properties.getProperty("driver");
			url=properties.getProperty("url");
			uname=properties.getProperty("uname");
			pwd=properties.getProperty("pwd");
			
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取连接
	public static Connection getConn(){
		try {
			return DriverManager.getConnection(url, uname, pwd);
		} catch (Exception e) {
			System.out.println("获取连接出错!");
			e.printStackTrace();
		}
		return null;
	}
	//关闭连接   可变长参数
	public static void close(Object...params){
		if(params!=null){
			for(Object param:params){
				try {
					if(param instanceof Connection){
						((Connection)param).close();
					}else if(param instanceof PreparedStatement){
						((PreparedStatement)param).close();
					}else if(param instanceof ResultSet){
						((ResultSet)param).close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
