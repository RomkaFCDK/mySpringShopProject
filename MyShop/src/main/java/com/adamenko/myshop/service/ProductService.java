package com.adamenko.myshop.service;

import com.adamenko.myshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBasket(Long productId, String username);
    void deleteById(Long id);
}
