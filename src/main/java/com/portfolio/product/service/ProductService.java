package com.portfolio.product.service;

import java.util.List;

import com.portfolio.product.dto.ProductDto.ProductDetailResult;
import com.portfolio.product.dto.ProductDto.ProductListResult;
import com.portfolio.product.dto.ProductServiceDto.ProductListServiceDto;

public interface ProductService {

	List<ProductListResult> getProductList(ProductListServiceDto rq) throws Exception;
	
	ProductDetailResult getProductInfo(Integer ProductId) throws Exception;


}
