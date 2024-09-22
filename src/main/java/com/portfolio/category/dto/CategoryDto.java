package com.portfolio.category.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {
	private Integer categoryId;
	private String name;
	private String description;
	private String useYn;
    private Timestamp createDate;
    private Timestamp modifyDate;
	

}
