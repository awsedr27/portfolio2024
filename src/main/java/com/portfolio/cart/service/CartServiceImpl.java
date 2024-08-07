package com.portfolio.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.cart.dao.CartDao;
import com.portfolio.cart.dto.CartDto.Cart;
import com.portfolio.cart.dto.CartDto.CartListDeleteQuery;
import com.portfolio.cart.dto.CartDto.CartListQuery;
import com.portfolio.cart.dto.CartDto.CartListResult;
import com.portfolio.cart.dto.CartDto.CartQuery;
import com.portfolio.cart.dto.CartDto.CartSaveQuery;
import com.portfolio.cart.dto.CartDto.CartUpdateQuery;
import com.portfolio.cart.dto.CartServiceDto.CartListDeleteServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartSaveServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartUpdateServiceDto;
import com.portfolio.common.UserContext;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartDao cartDao;
	
	@Autowired
	UserContext userContext;

	@Override
	public int saveCart(CartSaveServiceDto rq) throws Exception {
		String userId=userContext.getUserInfo().getUserId();
		CartSaveQuery cartSaveQuery =new CartSaveQuery(rq);
		cartSaveQuery.setUserId(userId);
		cartDao.insertCart(cartSaveQuery);
		
		CartQuery cartQuery=new CartQuery();
		cartQuery.setProductId(rq.getProductId());
		cartQuery.setUserId(userId);
		
		Cart cart=cartDao.selectCart(cartQuery);
		
		return cart.getQuantity();
	}

	@Override
	public int getCartListCnt() {
		return cartDao.selectCartListCnt(userContext.getUserInfo().getUserId());
	}

	@Override
	public List<CartListResult> getCartList() {
		CartListQuery cartListQuery=new CartListQuery();
		cartListQuery.setUserId(userContext.getUserInfo().getUserId());
		return cartDao.selectCartList(cartListQuery);

	}

	@Override
	public Cart updateCart(CartUpdateServiceDto cartUpdateServiceDto) {
		String userId=userContext.getUserInfo().getUserId();
		CartUpdateQuery cartUpdateQuery=new CartUpdateQuery(cartUpdateServiceDto);
		cartUpdateQuery.setUserId(userId);
		cartDao.updateCart(cartUpdateQuery);
		
		CartQuery cartQuery=new CartQuery();
		cartQuery.setProductId(cartUpdateServiceDto.getProductId());
		cartQuery.setUserId(userId);
		Cart cart=cartDao.selectCart(cartQuery);
		return cart;
	}

	@Override
	public int deleteCartList(CartListDeleteServiceDto cartListDeleteServiceDto) {
		CartListDeleteQuery cartListDeleteQuery=new CartListDeleteQuery(cartListDeleteServiceDto);
		cartListDeleteQuery.setUserId(userContext.getUserInfo().getUserId());
		
		return cartDao.deleteCartList(cartListDeleteQuery);
	}

}
