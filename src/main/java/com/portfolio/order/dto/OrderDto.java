package com.portfolio.order.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.MyPageReviewListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private Integer orderId;
	private String userId;
	private Integer totalPrice;
	private String status;
    private Timestamp createDate;
    private Timestamp modifyDate;
    
    @Getter
    @Setter
    public static class OrderItemDto {
        private Integer orderItemId;
        private Integer orderId;
        private Integer productId;
        private Integer quantity;
        private Integer price;
        private String status;
        private Timestamp createDate;
        private Timestamp modifyDate;
    }
    @Getter
    @Setter
	public static class OrderListQuery {
    	private Integer orderId;
    	private String status;
    	private String userId;
	    private int limit;
	    public OrderListQuery() {
	    	
	    }
		public OrderListQuery(OrderListServiceDto orderListServiceDto) {
			this.status=orderListServiceDto.getStatus();
			this.orderId=orderListServiceDto.getOrderId();
		}
	}
    @Getter
    @Setter
    public static class OrderItemResult {
        private Integer orderItemId;
        private String imageUrl;
        private String productName;
        private Integer quantity;
        private Integer price;
        private String status;
        private Timestamp createDate;
    }
    @Getter
    @Setter
	public static class OrderListResult {
        private Integer orderId;
        private Integer totalPrice;
        private String status;
        private String postcode;
        private String roadAddress;
        private String jibunAddress;
        private String detailAddress;
        private Timestamp createDate;
        private List<OrderItemResult> orderItems;
	}
    @Getter
    @Setter
	public static class OrderCancelQuery {
        public OrderCancelQuery() {

		}
        public OrderCancelQuery(OrderCancelServiceDto orderCancelServiceDto) {
        	this.orderId=orderCancelServiceDto.getOrderId();
		}

		private Integer orderId;
	}
    @Getter
    @Setter
	public static class OrderItemsCancelByOrderIdQuery {
        public OrderItemsCancelByOrderIdQuery() {

		}
        public OrderItemsCancelByOrderIdQuery(Integer orderId) {
        	this.orderId=orderId;
		}

		private Integer orderId;
	}
    @Getter
    @Setter
	public static class OrderItemCancelQuery {
        public OrderItemCancelQuery() {

		}
        public OrderItemCancelQuery(Integer orderItemId) {
        	this.orderItemId=orderItemId;
		}

		private Integer orderItemId;
	}
    @Getter
    @Setter
	public static class OrderSaveQuery {
    	private Integer orderId;
    	private String userId;
    	private Integer totalPrice;
    	private String status;
    	
        private String postcode;
        private String roadAddress;
        private String jibunAddress;
        private String detailAddress;
	}
    @Getter
    @Setter
	public static class OrderItemsSaveQuery {
    	private Integer orderId;
    	private List<OrderItemSaveQuery> orderItems;
    	private String status;
	}
    @Getter
    @Setter
	public static class OrderItemSaveQuery {
    	private Integer productId;
    	private Integer quantity;
    	private Integer price;
    	public OrderItemSaveQuery() {
    		
    	}
    	public OrderItemSaveQuery(Integer productId,Integer quantity,Integer price) {
    		this.productId=productId;
    		this.quantity=quantity;
    		this.price=price;
    	}
	}
    @Getter
    @Setter
	public static class MyPageReviewListQuery {
    	public MyPageReviewListQuery() {
    		
    	}
    	public MyPageReviewListQuery(MyPageReviewListServiceDto serviceDto) {
			this.orderItemId=serviceDto.getOrderItemId();
			this.type=serviceDto.getType();
		}
		private String userId;
    	private String type;
    	private Integer orderItemId;
    	private String orderItemStatus;
    	private int limit;
	}
    @Getter
    @Setter
	public static class MyPageReviewListResult {
        private String imageUrl; 
        private String productName; 
        private Integer quantity; 
        private Integer price; 
        private Integer reviewId; 
        private String reviewUseYn; 
        private Integer reviewRating; 
        private String reviewComment;
        private String reviewReply; 
        private Integer orderItemId; 
        private Timestamp orderItemCreateDate;
	}
}
