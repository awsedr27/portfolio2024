package com.portfolio.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.category.dao.CategoryDao;
import com.portfolio.category.dto.CategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryDao categoryDao;

	@Override
	public List<CategoryDto> getCategoryList() {
		return categoryDao.selectCategoryList();
		
	}

}
