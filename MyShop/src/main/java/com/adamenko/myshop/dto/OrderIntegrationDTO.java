package com.adamenko.myshop.dto;

import com.adamenko.myshop.domain.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderIntegrationDTO {
    private Long orderId;
    private String username;
    private String address;
    private List<OrderDetailsDto> details;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailsDto {
        private String product;
        private Double price;
        private Double amount;
        private Double sum;

        public OrderDetailsDto(OrderDetails details) {
            this.product = details.getProduct().getTitle();
            this.price = details.getPrice().doubleValue();
            this.amount = details.getAmount().doubleValue();
            this.sum = details.getPrice().multiply(details.getAmount()).doubleValue();
        }
    }
}
