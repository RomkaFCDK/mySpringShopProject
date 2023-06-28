package com.adamenko.myshop.service;

import com.adamenko.myshop.dao.ProductRepository;
import com.adamenko.myshop.domain.Basket;
import com.adamenko.myshop.domain.User;
import com.adamenko.myshop.dto.ProductDTO;
import com.adamenko.myshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BasketService basketService;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BasketService basketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.basketService = basketService;    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    @Transactional
    public void addToUserBasket(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw  new RuntimeException("User not found: " + username);

        }

        Basket basket = user.getBasket();
        if(basket == null){
            Basket newBasket = basketService.createBasket(user, Collections.singletonList(productId));
            user.setBasket(newBasket);
            userService.save(user);
        }else {
            basketService.addProducts(basket, Collections.singletonList(productId));
        }
    }

    @Override
    public void deleteById(Long id) {

    }
}
