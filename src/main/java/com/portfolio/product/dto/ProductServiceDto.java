package com.portfolio.product.dto;

import lombok.Getter;
import lombok.Setter;

public class ProductServiceDto {
    @Getter
    @Setter
	public static class ProductListServiceDto {
	    private Integer categoryId;
	    private Integer productId;
	    private int limit;
	    public ProductListServiceDto() {
	    	
	    }
	    public ProductListServiceDto(ProductRequest.ProductListRequest request) {
	    	this.productId=request.getProductId();
	    	this.categoryId=request.getCategoryId();
	    }
	    
	}
    
}
