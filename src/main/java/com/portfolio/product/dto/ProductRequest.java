package com.portfolio.product.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ProductRequest {
	
    @Getter
    @Setter
	public static class ProductListRequest {
        private Timestamp  createDate;
        private Integer categoryId;
	}

}
