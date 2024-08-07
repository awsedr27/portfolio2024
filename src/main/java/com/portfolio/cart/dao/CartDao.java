package com.portfolio.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.cart.dto.CartDto.Cart;
import com.portfolio.cart.dto.CartDto.CartListDeleteQuery;
import com.portfolio.cart.dto.CartDto.CartListQuery;
import com.portfolio.cart.dto.CartDto.CartListResult;
import com.portfolio.cart.dto.CartDto.CartQuery;
import com.portfolio.cart.dto.CartDto.CartSaveQuery;
import com.portfolio.cart.dto.CartDto.CartUpdateQuery;

@Mapper
public interface CartDao {

	int insertCart(CartSaveQuery cartSaveQuery) ;

	int selectCartListCnt(String userId);

	Cart selectCart(CartQuery cartQuery);

	List<CartListResult> selectCartList(CartListQuery cartListQuery);

	int updateCart(CartUpdateQuery cartUpdateQuery);

	int deleteCartList(CartListDeleteQuery cartListDeleteQuery);

}
