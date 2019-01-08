package com.spirit.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.spirit.dao.OrderDao;
import com.spirit.domain.Order;
import com.spirit.domain.OrderItem;
import com.spirit.domain.PageBean;
import com.spirit.domain.Product;
import com.spirit.utils.DataSourceUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class OrderDaoImpl implements OrderDao {

	/**
	 * 保存订单
	 */
	public void save(Order o) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, 
				o.getOid(),o.getOrdertime(),o.getTotal(),
				o.getState(),o.getAddress(),o.getName(),
				o.getTelephone(),o.getUser().getUid()
				);
	}

	@Override
	/**
	 * 保存订单项
	 */
	public void saveItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,
				oi.getItemid(),oi.getCount(),oi.getSubtotal(),
				oi.getProduct().getPid(),oi.getOrder().getOid()
				);
	}

	/**
	 * 获取我的订单的总条数
	 */
	public int getTotalRecord(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	/**
	 * 获取我的订单 当前页数据
	 */
	public List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid)
			throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//查询所有订单（基本信息）
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid,pb.getStartIndex(),pb.getPageSize());
		
		//遍历订单集合 获取每一个订单，查询每个订单项
		for (Order order : list) {
			sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
			List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), order.getOid());
			
			//获取maplist	  获取每一个订单项详情,封装成orderitem,将其加入当前订单的订单项列表中
			for (Map < String, Object > map : maplist){
				//1.封装orderitem
				//a.闯将orderitem
				OrderItem oi = new OrderItem();
				
				//b.封装orderitem
				BeanUtils.populate(oi, map);
				
				//c.手动封装product
				Product p = new Product();
				
				BeanUtils.populate(p, map);
				
				oi.setProduct(p);
				
				//2.将orderitem放入order的订单列表
				order.getItems().add(oi);
			}
		}
		return list;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		//1.查询订单基本信息
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where oid =?";
		
		Order order = qr.query(sql, new BeanHandler<>(Order.class),oid);
		//System.out.println("查询订单："+order);
		//2.查询订单项
		sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
		//所有的订单详情
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(),oid);
		
		//System.out.println("数据库查询的条数："+mapList.size());
		//遍历获取每一个订单详情 封装成orderitem  加入到当前订单的集合中
		for (Map<String, Object> map : mapList) {
			//创建orderitem
			OrderItem oi = new OrderItem();
			
			//封装
			BeanUtils.populate(oi, map);
			
			//手动封装product
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			oi.setProduct(p);
			
			//将orderitem加入到订单的items集合
			order.getItems().add(oi);
			
		}
		return order;
	}

	@Override
	/**
	 * 
	 */
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state = ?,address = ?,name = ?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}

	@Override
	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		
		//判断state是否为空
		if (null == state || state.trim().length() == 0) {
			sql += " order by ordertime desc";
			return qr.query(sql, new BeanListHandler<>(Order.class));
		}
		
		sql += " where state  = ? order by ordertime desc";
		return qr.query(sql, new BeanListHandler<>(Order.class),state);
	}

}
