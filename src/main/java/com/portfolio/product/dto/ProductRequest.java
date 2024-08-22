package com.portfolio.product.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class ProductRequest {
	
    @Getter
    @Setter
	public static class ProductListRequest {
    	private Integer productId;
        private Integer categoryId;
	}
    
    @Getter
    @Setter
	public static class ProductDetailRequest {
    	
    	@NotNull(message = "productId cannot be null")
        @Min(value = 0, message = "productId must be greater than or equal to 0")
    	private Integer productId;
	}


}
