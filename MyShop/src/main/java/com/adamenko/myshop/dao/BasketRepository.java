package com.adamenko.myshop.dao;

import com.adamenko.myshop.domain.Basket;
import com.adamenko.myshop.domain.Product;
import com.adamenko.myshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByOrderById();
    List<Basket> findAllByUserOrderById(User user);

    Basket findByUserAndProductsIn(User user, List<Product> products);

    @Transactional
    void delete(Basket basket);

}

