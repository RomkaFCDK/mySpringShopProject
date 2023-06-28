package com.adamenko.myshop.service;

import com.adamenko.myshop.domain.Basket;
import com.adamenko.myshop.domain.User;
import com.adamenko.myshop.dto.BasketDTO;

import java.util.List;

public interface BasketService {
    Basket createBasket(User user, List<Long> productIds);
    void addProducts(Basket basket, List<Long> productIds);

    BasketDTO getBasketByUser(String name);

    void commitBasketToOrder(String username);

    void removeProductFromBasket(Long id);
}
