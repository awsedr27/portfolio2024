package com.portfolio.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.product.dto.ProductDto.Product;
import com.portfolio.product.dto.ProductDto.ProductDetailResult;
import com.portfolio.product.dto.ProductDto.ProductListQuery;
import com.portfolio.product.dto.ProductDto.ProductListResult;
import com.portfolio.product.dto.ProductDto.ProductUpdateQuantityQuery;

@Mapper
public interface ProductDao {

	List<ProductListResult> selectProductList(ProductListQuery rq);
	ProductDetailResult selectProductDetail(Integer productId);
	Product selectProduct(Integer productId);
	Product selectProductWithExclusiveLock(Integer productId);
	List<Product> selectProductListWithExclusiveLock(List<Integer> productIdList);
	int updateQuantitiesMinus(List<ProductUpdateQuantityQuery> productList);
	int updateQuantitiesPlus(List<ProductUpdateQuantityQuery> productList);

}
