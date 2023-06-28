package com.adamenko.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketDTO {
    private int amountProducts;
    private Double sum;
    private List<BasketDetailDTO> basketDetails = new ArrayList<>();

    public void aggregate(){
        this.amountProducts = basketDetails.size();
        this.sum = basketDetails.stream()
                .map(BasketDetailDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
