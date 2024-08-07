package com.portfolio.cart.dto;

import java.util.List;

import com.portfolio.cart.dto.CartDto.Cart;
import com.portfolio.cart.dto.CartDto.CartListResult;

import lombok.Getter;
import lombok.Setter;

public class CartResponse {

	@Setter
	@Getter
	public static class CartSaveResponse{
		private Integer quantity;
	}
	@Setter
	@Getter
	public static class CartListResponse{
		private List<CartListResult> cartList;
	}
	@Setter
	@Getter
	public static class CartUpdateResponse{
		public CartUpdateResponse() {
			
		}
		
		public CartUpdateResponse(Cart result) {
			this.quantity=result.getQuantity();
		}

		private Integer quantity;
	}
}
