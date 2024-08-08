package com.portfolio.order.dto;

import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @Getter
    @Setter
	public static class OrderItemCancelRequest {
    	@NotNull(message = "orderItemId cannot be null")
    	private Integer orderItemId;
	}
    @Getter
    @Setter
	public static class OrderSaveRequest {
        @NotEmpty(message = "orderSaveList cannot be empty")
    	List<@Valid OrderItemSaveRequest> orderSaveList;
	}
    @Getter
    @Setter
    public static class OrderItemSaveRequest {
    	@NotNull(message = "Product ID cannot be null")
        @Min(value = 0, message = "productId must be greater than or equal to 0")
    	private Integer productId;
    	@NotNull(message = "quantity cannot be null")
        @Min(value = 1, message = "quantity must be greater than or equal to 1")
    	private Integer quantity;
	}
}
