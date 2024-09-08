package com.portfolio.order.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
            regexp = "PENDING|PROCESSING|SHIPPED|COMPLETED|CANCELLED|",
            message = "Invalid order status"
        )
        private String status;
        @Min(value = 1, message = "orderId must be greater than or equal to 1")
    	private Integer orderId;
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
        private List<@Valid OrderItemSaveRequest> orderSaveList;
        @NotBlank(message = "postcode cannot be blank")
        private String postcode;
        @NotBlank(message = "roadAddress cannot be blank")
        private String roadAddress;
        @NotBlank(message = "jibunAddress cannot be blank")
        private String jibunAddress;
        @NotBlank(message = "detailAddress cannot be blank")
        private String detailAddress;
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
    @Getter
    @Setter
    public static class MyPageReviewListRequest {
    	@NotBlank(message = "type ID cannot be null")
        @Pattern(
                regexp = "REVIEWABLE|REVIEWED",
                message = "Invalid orderItem review type"
        )
    	private String type;
        @Min(value = 0, message = "orderItemId must be greater than or equal to 0")
    	private Integer orderItemId;
	}
}
