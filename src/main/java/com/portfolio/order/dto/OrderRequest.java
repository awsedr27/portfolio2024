package com.portfolio.order.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class OrderRequest {
    @Getter
    @Setter
	public static class OrderListRequest {
        @Pattern(
            regexp = "PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED",
            message = "Invalid order status"
        )
        private String status;
        private Timestamp createDate;
	}
    @Getter
    @Setter
	public static class OrderCancelRequest {
    	@NotNull(message = "orderId cannot be null")
    	private Integer orderId;
	}
}
