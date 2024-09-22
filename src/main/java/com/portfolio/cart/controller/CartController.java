package com.portfolio.cart.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.cart.dto.CartDto.Cart;
import com.portfolio.cart.dto.CartDto.CartListForCheckoutResult;
import com.portfolio.cart.dto.CartDto.CartListResult;
import com.portfolio.cart.dto.CartRequest.CartBuyNowRequest;
import com.portfolio.cart.dto.CartRequest.CartListForCheckoutRequest;
import com.portfolio.cart.dto.CartRequest.CartListDeleteRequest;
import com.portfolio.cart.dto.CartRequest.CartSaveRequest;
import com.portfolio.cart.dto.CartRequest.CartUpdateRequest;
import com.portfolio.cart.dto.CartResponse.CartListForCheckoutResponse;
import com.portfolio.cart.dto.CartResponse.CartListResponse;
import com.portfolio.cart.dto.CartResponse.CartSaveResponse;
import com.portfolio.cart.dto.CartResponse.CartUpdateResponse;
import com.portfolio.cart.dto.CartServiceDto.CartBuyNowServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartListDeleteServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartSaveServiceDto;
import com.portfolio.cart.dto.CartServiceDto.CartUpdateServiceDto;
import com.portfolio.cart.service.CartService;
import com.portfolio.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {
	
	@Autowired
	CartService cartService;
	
    @PostMapping("/save")
    public ResponseEntity<?> cartSave(@Valid @RequestBody  CartSaveRequest CartSaveRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		CartSaveServiceDto rq =new CartSaveServiceDto(CartSaveRequest);
    		int result=cartService.saveCart(rq);
    		CartSaveResponse cartSaveResponse=new CartSaveResponse();
    		cartSaveResponse.setQuantity(result);
    	    return ResponseEntity.status(HttpStatus.OK).body(cartSaveResponse);
    	}catch (Exception e) {
    		log.error("장바구니 추가 실패"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니에 추가하는것을 실패했습니다");
		}
    
    }
    @PostMapping("/list/count")
    public ResponseEntity<?> cartListCnt(HttpServletResponse httpServletResponse) {
    	try {
    		int result=cartService.getCartListCnt();
    	    return ResponseEntity.status(HttpStatus.OK).body(result);
    	}catch (Exception e) {
    		log.error("장바구니 개수를 가져오는데 실패했습니다"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 개수를 가져오는데 실패했습니다");
		}
    
    }
    
    @PostMapping("/list")
    public ResponseEntity<?> cartList(HttpServletResponse httpServletResponse) {
    	try {
    		List<CartListResult> result=cartService.getCartList();
    		CartListResponse cartListResponse=new CartListResponse();
    		cartListResponse.setCartList(result);
    	    return ResponseEntity.status(HttpStatus.OK).body(cartListResponse);
    	}catch (Exception e) {
    		log.error("장바구니 목록을 불러오는데 실패했습니다"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 목록을 불러오는데 실패했습니다");
		}
    
    }
    @PostMapping("/update")
    public ResponseEntity<?> cartUpdate(@Valid @RequestBody  CartUpdateRequest cartUpdateRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		CartUpdateServiceDto cartUpdateServiceDto=new CartUpdateServiceDto(cartUpdateRequest);
    		Cart result=cartService.updateCart(cartUpdateServiceDto);
    		CartUpdateResponse cartUpdateResponse=new CartUpdateResponse(result);
    	    return ResponseEntity.status(HttpStatus.OK).body(cartUpdateResponse);
    	}catch (Exception e) {
    		log.error("장바구니 정보를 수정에 실패했습니다"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 정보를 수정에 실패했습니다");
		}
    
    }
    @PostMapping("/list/delete")
    public ResponseEntity<?> cartListDelete(@Valid @RequestBody  CartListDeleteRequest cartListDeleteRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		CartListDeleteServiceDto cartDeleteServiceDto=new CartListDeleteServiceDto(cartListDeleteRequest);
    		int result=cartService.deleteCartList(cartDeleteServiceDto);
    	    return ResponseEntity.status(HttpStatus.OK).body("장바구니 삭제에 성공했습니다");
    	}catch (Exception e) {
    		log.error("장바구니 삭제에 실패했습니다"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 삭제에 실패했습니다");
		}
    
    }
    @PostMapping("/buy-now")
    public ResponseEntity<?> cartBuyNow(@Valid @RequestBody  CartBuyNowRequest cartBuyNowRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		CartBuyNowServiceDto cartBuyNowServiceDto=new CartBuyNowServiceDto(cartBuyNowRequest);
    		cartService.buyNow(cartBuyNowServiceDto);
    	    return ResponseEntity.status(HttpStatus.OK).body(null);
    	}catch (Exception e) {
    		log.error("즉시구매로 장바구니 추가에 실패했습니다"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("즉시구매로 장바구니 추가에 실패했습니다");
		}
    
    }
    @PostMapping("/list/checkout")
    public ResponseEntity<?> cartDetailsForCheckout(@Valid @RequestBody  CartListForCheckoutRequest cartListForCheckoutRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		List<CartListForCheckoutResult> list=cartService.getCartListForCheckout(cartListForCheckoutRequest.getProductIdList());
    		CartListForCheckoutResponse rs=new CartListForCheckoutResponse();
    		rs.setCartList(list);
    		return ResponseEntity.status(HttpStatus.OK).body(rs);
    	}catch (Exception e) {
    		log.error("구매페이지 장바구니 데이터를 불러오는데 실패했습니다"+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구매페이지 장바구니 데이터를 불러오는데 실패했습니다");
		}
    
    }
}
