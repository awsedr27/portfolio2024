package com.portfolio.product.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ProductResponse {
	
    @Getter
    @Setter
	public static class ProductListResponse{
		List<ProductItemResponse> productList;
	}
    
    @Getter
    @Setter
	public static class ProductItemResponse{
	    private Integer productId;
	    private String name;
	    private String description;
	    private Integer price;
	    private Integer categoryId;
	    private Timestamp createDate;
	}

}
