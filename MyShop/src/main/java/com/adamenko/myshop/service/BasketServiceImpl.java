package com.adamenko.myshop.service;

import com.adamenko.myshop.dao.BasketRepository;
import com.adamenko.myshop.dao.ProductRepository;
import com.adamenko.myshop.domain.*;
import com.adamenko.myshop.dto.BasketDTO;
import com.adamenko.myshop.dto.BasketDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderService orderService;

    public BasketServiceImpl(BasketRepository basketRepository, ProductRepository productRepository, UserService userService, OrderService orderService) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public Basket createBasket(User user, List<Long> productIds) {
        Basket basket = new Basket();
        basket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        basket.setProducts(productList);
        return basketRepository.save(basket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds){
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Basket basket, List<Long> productIds) {
        List<Product> products = basket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        basket.setProducts(newProductList);
        basketRepository.save(basket);
    }

    @Override
    public BasketDTO getBasketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBasket() == null){
            return new BasketDTO();
        }
        BasketDTO basketDTO = new BasketDTO();
        Map<Long, BasketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBasket().getProducts();
        for (Product product : products){
            BasketDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new BasketDetailDTO(product));
            }else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        basketDTO.setBasketDetails(new ArrayList<>(mapByProductId.values()));
        basketDTO.aggregate();

        return basketDTO;
    }

    @Override
    @Transactional
    public void commitBasketToOrder(String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("this user not found!");
        }
        Basket basket = user.getBasket();
        if (basket == null || basket.getProducts().isEmpty()) {
            return;
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);


        Map<Product, Long> productWithAmount = basket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order);
        basket.getProducts().clear();
        basketRepository.save(basket);
    }

    @Override
    public void removeProductFromBasket(Long id) {
        Basket basket = basketRepository.findById(id).orElseThrow();
        basketRepository.delete(basket);

    }

}
