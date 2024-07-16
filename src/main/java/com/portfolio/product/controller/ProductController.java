package com.portfolio.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.product.dto.ProductDto.ProductInfo;
import com.portfolio.product.dto.ProductRequest;
import com.portfolio.product.dto.ProductResponse;
import com.portfolio.product.dto.ProductResponse.ProductItemResponse;
import com.portfolio.product.dto.ProductResponse.ProductListResponse;
import com.portfolio.product.service.ProductService;
import com.portfolio.user.dto.UserResponse;
import com.portfolio.user.dto.UserRequest.NaverLoginRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
	
	@Autowired
	ProductService productService;
	
    @PostMapping("/list")
    public ResponseEntity<?> productList(@Valid @RequestBody ProductRequest.ProductListRequest productListRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		ProductInfo rq =new ProductInfo(productListRequest);
    		ProductListResponse rs=new ProductListResponse();
    		List<ProductItemResponse> result=productService.getProductList(rq);
    		rs.setProductList(result);
    	    return ResponseEntity.status(HttpStatus.OK).body(rs);
    	}catch (Exception e) {
    		log.error("상품 목록 불러오기 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 목록을 불러오는데 실패했습니다");
		}
    
    }

}
