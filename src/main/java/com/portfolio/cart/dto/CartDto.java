package com.portfolio.cart.dto;

import java.sql.Timestamp;
import java.util.List;

import com.portfolio.cart.dto.CartServiceDto.CartListDeleteServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartSaveServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartUpdateServiceDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    @Getter
    @Setter
	public static class CartQuery {
	    private String userId;
	    private Integer productId;
	}
    @Getter
    @Setter
	public static class Cart {
	    private String userId;
	    private Integer productId;
	    private Integer quantity;
	    private Timestamp createDate;
	    private Timestamp modifyDate;
	}
    
    @Getter
    @Setter
	public static class CartSaveQuery {
	    private Integer productId;
	    private String userId;
	    
	    public CartSaveQuery(){
	    	
	    }
	    public CartSaveQuery(CartSaveServiceDto cartSaveServiceDto){
	    	this.productId=cartSaveServiceDto.getProductId();
	    }
	}



    @Getter
    @Setter
	public static class CartListQuery {
	    private String userId;
	}
    @Getter
    @Setter
	public static class CartListResult {
	    private Integer productId;
	    private String productName;
	    private Integer quantity;
	    private Timestamp createDate;
	}
    @Getter
    @Setter
	public static class CartUpdateQuery {
    	public CartUpdateQuery() {
    		
    	}
	    public CartUpdateQuery(CartUpdateServiceDto cartUpdateServiceDto) {
			this.productId=cartUpdateServiceDto.getProductId();
			this.quantity=cartUpdateServiceDto.getQuantity();
		}
		private Integer productId;
	    private Integer quantity;
	    private String userId;
	}
    @Getter
    @Setter
	public static class CartListDeleteQuery {
    	public CartListDeleteQuery() {
    		
    	}
	    public CartListDeleteQuery(CartListDeleteServiceDto cartListDeleteServiceDto) {
	    	this.productIdList=cartListDeleteServiceDto.getProductIdList();
		}
	    private List<Integer> productIdList;
	    private String userId;
	}
    

}
