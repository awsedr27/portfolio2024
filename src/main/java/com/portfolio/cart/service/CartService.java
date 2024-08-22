package com.portfolio.cart.service;

import java.util.List;

import com.portfolio.cart.dto.CartDto.Cart;
import com.portfolio.cart.dto.CartDto.CartListForCheckoutResult;
import com.portfolio.cart.dto.CartDto.CartListResult;
import com.portfolio.cart.dto.CartServiceDto.CartBuyNowServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartListDeleteServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartSaveServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartUpdateServiceDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public interface CartService {

	int saveCart(CartSaveServiceDto rq) throws Exception;

	int getCartListCnt();

	List<CartListResult> getCartList();

	Cart updateCart(CartUpdateServiceDto cartUpdateServiceDto);

	int deleteCartList(CartListDeleteServiceDto cartDeleteServiceDto);

	void buyNow(CartBuyNowServiceDto cartBuyNowServiceDto);

	List<CartListForCheckoutResult> getCartListForCheckout(List<Integer> productIdList);

}
