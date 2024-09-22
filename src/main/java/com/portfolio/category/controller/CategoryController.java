package com.portfolio.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.category.dto.CategoryDto;
import com.portfolio.category.dto.CategoryResponse.CategoryListResponse;
import com.portfolio.category.service.CategoryService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
    @PostMapping("/list")
    public ResponseEntity<?> categoryList(HttpServletResponse httpServletResponse) {
    	try {
    		List<CategoryDto> result=categoryService.getCategoryList();
    		CategoryListResponse categoryListResponse=new CategoryListResponse();
    		categoryListResponse.setCategoryList(result);
    	    return ResponseEntity.status(HttpStatus.OK).body(categoryListResponse);
    	}catch (Exception e) {
    		log.error("카테고리 리스트를 불러오는데 실패"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카테고리 리스트를 불러오는데 실패했습니다");
		}
    }
}
