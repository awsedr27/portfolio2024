package com.portfolio.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portfolio.product.dao.ProductDao;
import com.portfolio.product.dto.ProductDto.ProductInfo;
import com.portfolio.product.dto.ProductResponse.ProductItemResponse;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductDao productDao;
	
    @Value("${product.paging.size}")
    private int pagingSize;

	@Override
	public List<ProductItemResponse> getProductList(ProductInfo rq)throws Exception {
		rq.setLimit(pagingSize);
		return productDao.selectProductList(rq);
	}



}
