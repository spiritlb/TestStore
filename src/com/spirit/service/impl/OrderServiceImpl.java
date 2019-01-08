package com.spirit.service.impl;

import java.util.List;

import javax.sql.DataSource;

import com.spirit.dao.OrderDao;
import com.spirit.domain.Order;
import com.spirit.domain.OrderItem;
import com.spirit.domain.PageBean;
import com.spirit.service.OrderService;
import com.spirit.utils.BeanFactory;
import com.spirit.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	/**
	 * 保存订单
	 */
	public void save(Order order) throws Exception {
		try {
			//获取dao
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			//1.开始事务
			DataSourceUtils.startTransaction();
			
			//2.向order表中插入一条
			od.save(order);
			
			//3.向orderitem中插入n条
			for(OrderItem oi : order.getItems()){
				od.saveItem(oi);
			}
			//4.事务控制
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	/**
	 * 我的订单
	 */
	public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize,
			String uid) throws Exception {
		//获取dao
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		//1.创建pagebean
		PageBean<Order> pb =  new PageBean<>(pageNumber,pageSize);
		
		//2.查询总条数	设置总条数  
		int totalRecord = od.getTotalRecord(uid);
		pb.setTotalRecord(totalRecord);
		
		//3.查询当前页数据	设置当前页数据
		List<Order> data = od.findMyOrdersByPage(pb,uid);
		pb.setData(data);
		
		return pb;
	}

	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		OrderDao  od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}

	/**
	 * 修改订单
	 */
	public void update(Order order) throws Exception {
		OrderDao  od = (OrderDao) BeanFactory.getBean("OrderDao");	
		od.update(order);
	}

	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao  od = (OrderDao) BeanFactory.getBean("OrderDao");	
		return od.findAllByState(state);
	}

}
