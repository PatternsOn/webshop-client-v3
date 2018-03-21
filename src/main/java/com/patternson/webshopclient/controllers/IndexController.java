package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.ArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Created by Tobias Pettersson 20180321
 */
@Slf4j
@Controller
public class IndexController {
    CartController cartController;

    public IndexController(CartController cartController) {
        this.cartController = cartController;
    }

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles";

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArticleDTO[]> articleDTOResponseEntity = restTemplate.getForEntity(BASE_URI, ArticleDTO[].class);

        int quantity = cartController.getCartItemQuantity();

        model.addAttribute("quantity", quantity);
        model.addAttribute("articles", articleDTOResponseEntity.getBody());

        return "index";
    }
}


