package com.portfolio.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portfolio.product.dao.ProductDao;
import com.portfolio.product.dto.ProductDto.ProductDetailResult;
import com.portfolio.product.dto.ProductDto.ProductListQuery;
import com.portfolio.product.dto.ProductDto.ProductListResult;
import com.portfolio.product.dto.ProductServiceDto.ProductListServiceDto;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductDao productDao;
	
    @Value("${product.paging.size}")
    private int pagingSize;

	@Override
	public List<ProductListResult> getProductList(ProductListServiceDto rq)throws Exception {
		rq.setLimit(pagingSize);
		
		ProductListQuery ProductListQuery=new ProductListQuery(rq);
		return productDao.selectProductList(ProductListQuery);
	}

	@Override
	public ProductDetailResult getProductInfo(Integer ProductId) throws Exception {
		return productDao.selectProductDetail(ProductId);
	}



}
