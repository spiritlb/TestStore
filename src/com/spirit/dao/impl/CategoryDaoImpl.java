package com.spirit.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.spirit.dao.CategoryDao;
import com.spirit.domain.Category;
import com.spirit.utils.BaseDao3;
import com.spirit.utils.DataSourceUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class CategoryDaoImpl implements CategoryDao {

	/**
	 * 查询所有分类
	 */
	public List<Category> findAll() throws Exception {
		 return BaseDao3.queryAll("select * from category", Category.class);
	}

	/**
	 * 保存分类
	 */
	public void save(Category c) throws Exception {
		BaseDao3.changePos("insert into category values (?,?)",c.getCid(),c.getCname());
	}
}
