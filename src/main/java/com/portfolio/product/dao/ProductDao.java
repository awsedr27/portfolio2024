package com.portfolio.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.product.dto.ProductDto.ProductInfo;
import com.portfolio.product.dto.ProductResponse.ProductItemResponse;

@Mapper
public interface ProductDao {

	List<ProductItemResponse> selectProductList(ProductInfo rq);

}
