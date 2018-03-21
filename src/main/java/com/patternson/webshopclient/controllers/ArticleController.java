package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.exceptions.NotFoundException;
import com.patternson.webshopclient.model.ArticleDTO;
import com.patternson.webshopclient.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 *
 * Created by Tobias Pettersson 20180321
 */
@Slf4j
@Controller
public class ArticleController {

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";
    private RestTemplate restTemplate = new RestTemplate();
    private final ArticleService articleService;

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Visar en produkts värden i ett formulär
    @GetMapping("/article/{id}/articleupdateform")
    public String showArticleInUpdateFormById(@PathVariable Long id, Model model){
        ArticleDTO articleDTO = articleService.getArticleById(id);
        model.addAttribute("article", articleDTO);

        return "article/articleupdateform";
    }

    // Visar tomt formulär
    @GetMapping("/article/articlecreateform")
    public String showArticleCreateForm(Model model){
        model.addAttribute("article", new ArticleDTO());

        return "article/articlecreateform";
    }

    @PostMapping("article/{id}")
    public String updateArticle(@Valid @ModelAttribute("article") ArticleDTO articleDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "article/articleupdateform";
        }

        HttpEntity<ArticleDTO> entity = getAuthenticatedEntity(articleDTO);
        restTemplate.put(BASE_URI + "/{id}", entity, articleDTO.getId());

        return "redirect:/index/";
    }

    @PostMapping("/createarticle")
    public String createArticle(@Valid @ModelAttribute("article") ArticleDTO articleDTO, BindingResult bindingResult) { // Testar Bindingresukt och validation
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "article/articlecreateform";
        }

        HttpEntity<ArticleDTO> entity = getAuthenticatedEntity(articleDTO);
        restTemplate.postForObject(BASE_URI, entity, ArticleDTO.class);

        return "redirect:/index/";
    }

    @GetMapping("/article/{id}/confirmdelete")  // Stoppa in id istället
    public String confirmDeleteArticleById(@ModelAttribute ArticleDTO articleDTO, Model model) {
        model.addAttribute("article", articleDTO);

        return "article/confirmdelete";
    }

    @GetMapping("/article/canceldelete")
    public String cancelDelete() {

        return "redirect:/index/";
    }

    @PostMapping("/article/{id}/delete")
    public String deleteArticleById(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + oAuth2ClientContext.getAccessToken());
        HttpEntity entity = new HttpEntity(httpHeaders);

        restTemplate.exchange(BASE_URI + id, HttpMethod.DELETE, entity, Void.class, id);

        return "redirect:/index/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    private HttpEntity<ArticleDTO> getAuthenticatedEntity(ArticleDTO articleDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + oAuth2ClientContext.getAccessToken());
        return new HttpEntity<>(articleDTO, httpHeaders);
    }
}
