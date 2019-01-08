package com.spirit.service.impl;

import java.util.List;

import com.spirit.dao.ProductDao;
import com.spirit.dao.impl.ProductDaoImpl;
import com.spirit.domain.PageBean;
import com.spirit.domain.Product;
import com.spirit.service.ProductService;
import com.spirit.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	/**
	 * 查询热门商品
	 */
	public List<Product> findHot() throws Exception {
		//ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findHot();
	}
	/**
	 * 查询最新商品
	 */
	public List<Product> findNew() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findNew();
	}
	/**
	 * 单个商品详情
	 */
	public Product getById(String pid) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.getById(pid);
	}
	/**
	 * 分页展示商品
	 */
	public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid)
			throws Exception {
		ProductDao pDao = (ProductDao) BeanFactory.getBean("ProductDao");
		//1.创建pagebean
		PageBean<Product> pb = new PageBean<>(pageNumber,pageSize); 
		
		//2.设置当前页数据
		List<Product> data = pDao.findByPage(pb,cid);
		pb.setData(data);
		
		//3.设置总记录数
		int totalRecord = pDao.getTotalRecord(cid);
		pb.setTotalRecord(totalRecord);
		return pb;
	}
	
	/**
	 * 后台展示已上架商品
	 */
	public List<Product> findAll() throws Exception {
		ProductDao pDAO = (ProductDao) BeanFactory.getBean("ProductDao"); 
		return pDAO.findAll();
	}

	/**
	 * 保存商品
	 */
	public void save(Product p) throws Exception {
		ProductDao pDao = (ProductDao) BeanFactory.getBean("ProductDao");
		pDao.save(p);
	}

}
