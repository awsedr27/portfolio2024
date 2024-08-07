package com.portfolio.cart.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class CartRequest {
    @Getter
    @Setter
	public static class CartSaveRequest {
    	
    	@NotNull(message = "productId cannot be null")
        @Min(value = 0, message = "productId must be greater than or equal to 0")
    	private Integer productId;
	}
    @Getter
    @Setter
	public static class CartUpdateRequest {
    	@NotNull(message = "productId cannot be null")
        @Min(value = 0, message = "productId must be greater than or equal to 0")
    	private Integer productId;
    	
    	@Min(value = 0, message = "quantity must be greater than or equal to 0")
    	private Integer quantity;
	}
    @Getter
    @Setter
	public static class CartListDeleteRequest {
        @NotEmpty(message = "productIdList cannot be empty")
        private List<@NotNull(message = "productId cannot be null")
                     @Min(value = 0, message = "productId must be greater than or equal to 0") Integer> productIdList;
	}
}
