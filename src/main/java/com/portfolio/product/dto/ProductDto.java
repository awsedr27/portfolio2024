package com.portfolio.product.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ProductDto {
	
    @Getter
    @Setter
	public static class ProductInfo {
	    private Integer productId;
	    private String name;
	    private String description;
	    private String useYn;
	    private Integer price;
	    private Integer categoryId;
	    private Timestamp createDate;
	    private Timestamp modifyDate;
	    private int limit;
	    
	    public ProductInfo() {
	    	
	    }
	    public ProductInfo(ProductRequest.ProductListRequest request) {
	    	this.createDate=request.getCreateDate();
	    	this.categoryId=request.getCategoryId();
	    }
	}
}
