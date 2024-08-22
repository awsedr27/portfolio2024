package com.portfolio.cart.dto;

import java.util.List;

import com.portfolio.cart.dto.CartRequest.CartBuyNowRequest;
import com.portfolio.cart.dto.CartRequest.CartListDeleteRequest;
import com.portfolio.cart.dto.CartRequest.CartSaveRequest;
import com.portfolio.cart.dto.CartRequest.CartUpdateRequest;

import lombok.Getter;
import lombok.Setter;

public class CartServiceDto {
    @Getter
    @Setter
	public static class CartSaveServiceDto {
	    private Integer productId;
	    private Integer quantity;

	    public CartSaveServiceDto() {
	    	
	    }
	    public CartSaveServiceDto(CartSaveRequest request) {
	    	this.productId=request.getProductId();
	    	this.quantity=request.getQuantity();
	    }
	    
	}
    @Getter
    @Setter
	public static class CartUpdateServiceDto {
	    private Integer productId;
	    private Integer quantity;
	    public CartUpdateServiceDto() {
	    	
	    }
	    public CartUpdateServiceDto(CartUpdateRequest request) {
	    	this.productId=request.getProductId();
	    	this.quantity=request.getQuantity();
	    }
	    
	}
    @Getter
    @Setter
	public static class CartListDeleteServiceDto {
	    private List<Integer> productIdList;

	    public CartListDeleteServiceDto() {
	    	
	    }
	    public CartListDeleteServiceDto(CartListDeleteRequest request) {
	    	this.productIdList=request.getProductIdList();
	    }
	    
	}
    @Getter
    @Setter
	public static class CartBuyNowServiceDto {
	    private Integer productId;
	    private Integer quantity;

	    public CartBuyNowServiceDto() {
	    	
	    }
	    public CartBuyNowServiceDto(CartBuyNowRequest request) {
	    	this.productId=request.getProductId();
	    	this.quantity=request.getQuantity();
	    }
	    
	}
}
