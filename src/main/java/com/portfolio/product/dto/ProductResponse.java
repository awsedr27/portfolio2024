package com.portfolio.product.dto;

import java.sql.Timestamp;
import java.util.List;

import com.portfolio.product.dto.ProductDto.ProductDetailResult;

import lombok.Getter;
import lombok.Setter;

public class ProductResponse {
	
    @Getter
    @Setter
	public static class ProductListResponse{
		List<ProductDto.ProductListResult> productList;
	}
    

    
    @Getter
    @Setter
	public static class ProductDetailResponse{
	    private Integer productId;
	    private String name;
	    private String description;
	    private Integer price;
	    private Integer categoryId;
	    private Timestamp createDate;
	    
	    public ProductDetailResponse(){
	    	
	    }
	    public ProductDetailResponse(ProductDetailResult productDetail){
	    	if(productDetail!=null) {
				this.productId = productDetail.getProductId();
				this.name = productDetail.getName();
				this.description = productDetail.getDescription();
				this.price = productDetail.getPrice();
				this.categoryId = productDetail.getCategoryId();
				this.createDate = productDetail.getCreateDate();
	    	}
	    }

	}


}
