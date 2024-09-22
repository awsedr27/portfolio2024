package com.portfolio.category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.category.dto.CategoryDto;

@Mapper
public interface CategoryDao {
	List<CategoryDto> selectCategoryList();
}
