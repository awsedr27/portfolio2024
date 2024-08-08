package com.portfolio.product.dto;

import java.sql.Timestamp;

import com.portfolio.product.dto.ProductServiceDto.ProductListServiceDto;

import lombok.Getter;
import lombok.Setter;

public class ProductDto {
	
    @Getter
    @Setter
	public static class Product {
	    private Integer productId;
	    private Integer quantity;
	    private String name;
	    private String description;
	    private String useYn;
	    private Integer price;
	    private Integer categoryId;
	    private Timestamp createDate;
	    private Timestamp modifyDate;
	}
    @Getter
    @Setter
	public static class ProductDetailResult {
	    private Integer productId;
	    private String name;
	    private String description;
	    private Integer price;
	    private Integer categoryId;
	    private Timestamp createDate;
	}
    @Getter
    @Setter
	public static class ProductListQuery {
	    private Integer categoryId;
	    private Integer productId;
	    private int limit;
	    public ProductListQuery() {
	    	
	    }
	    public ProductListQuery(ProductListServiceDto request) {
	    	this.productId=request.getProductId();
	    	this.categoryId=request.getCategoryId();
	    	this.limit=request.getLimit();
	    }
	    
	}
    
    @Getter
    @Setter
	public static class ProductListResult {
	    private Integer productId;
	    private String name;
	    private String description;
	    private Integer price;
	    private Integer categoryId;
	    private Timestamp createDate;
	}
    
}
