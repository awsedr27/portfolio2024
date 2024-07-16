package com.portfolio.product.service;

import java.util.List;

import com.portfolio.product.dto.ProductDto.ProductInfo;
import com.portfolio.product.dto.ProductResponse.ProductItemResponse;

public interface ProductService {

	List<ProductItemResponse> getProductList(ProductInfo rq) throws Exception;

}
