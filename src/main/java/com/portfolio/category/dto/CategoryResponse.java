package com.portfolio.category.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    @Getter
    @Setter
	public static class CategoryListResponse{
		List<CategoryDto> categoryList;
	}
}
