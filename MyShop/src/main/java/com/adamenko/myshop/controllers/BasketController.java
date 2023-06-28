package com.adamenko.myshop.controllers;

import com.adamenko.myshop.dao.BasketRepository;
import com.adamenko.myshop.domain.Basket;
import com.adamenko.myshop.dto.BasketDTO;
import com.adamenko.myshop.service.BasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService){

        this.basketService = basketService;
    }
    @GetMapping("/basket")
    public String aboutBasket(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("basket", new BasketDTO());
        }else{
            BasketDTO basketDTO = basketService.getBasketByUser(principal.getName());
            model.addAttribute("basket", basketDTO);
        }
        return "basket";
    }

    @PostMapping("/basket")
    public String commitBasket(Principal principal){
        if(principal != null){
            basketService.commitBasketToOrder(principal.getName());
        }
        return "redirect:/basket";
    }
    @DeleteMapping("/basket/remove")
    public String removeFromBasket(@PathVariable(value = "id") long id,Principal principal) {
        BasketDTO basketDTO = basketService.getBasketByUser(principal.getName());
        basketService.removeProductFromBasket(id);

        return "redirect:/basket";
    }
}
