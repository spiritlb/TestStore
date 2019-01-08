package com.spirit.dao;

import com.spirit.domain.Category;

import java.util.List;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void save(Category c) throws Exception;

}
