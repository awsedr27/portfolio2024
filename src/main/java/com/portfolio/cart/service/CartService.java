package com.portfolio.cart.service;

import java.util.List;

import com.portfolio.cart.dto.CartDto.Cart;
import com.portfolio.cart.dto.CartDto.CartListResult;
import com.portfolio.cart.dto.CartServiceDto.CartListDeleteServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartSaveServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartUpdateServiceDto;

public interface CartService {

	int saveCart(CartSaveServiceDto rq) throws Exception;

	int getCartListCnt();

	List<CartListResult> getCartList();

	Cart updateCart(CartUpdateServiceDto cartUpdateServiceDto);

	int deleteCartList(CartListDeleteServiceDto cartDeleteServiceDto);

}
