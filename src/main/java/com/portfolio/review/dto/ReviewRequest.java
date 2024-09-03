package com.portfolio.review.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class ReviewRequest {
    @Getter
    @Setter
	public static class ReviewListRequest {
    	@NotNull(message = "productId cannot be null")
        @Min(value = 0, message = "productId must be greater than or equal to 0")
    	private Integer productId;
        @NotBlank(message = "sortBy cannot be null")
    	@Pattern(
                regexp = "LATEST|OLDEST|HIGHRATING|LOWRATING",
                message = "Invalid sortBy"
        )
        private String sortBy;
        @Min(value = 0, message = "reviewId must be greater than or equal to 0")
        private Integer reviewId;
        @Min(value = 1, message = "rating must be greater than or equal to 1")
        private Integer rating;
	}
    @Getter
    @Setter
	public static class ReviewSaveRequest {
    	@NotNull(message = "productId cannot be null")
        @Min(value = 0, message = "productId must be greater than or equal to 0")
    	private Integer productId;
    	@NotNull(message = "rating cannot be null")
        @Min(value = 1, message = "rating must be greater than or equal to 1")
    	private Integer rating;
    	@NotBlank(message = "comment cannot be null")
    	private String comment;
	}
}
