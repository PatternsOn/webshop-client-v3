package com.patternson.webshopclient.service;

import com.patternson.webshopclient.model.ArticleDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 * Created by Tobias Pettersson 20180321
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";
    private RestTemplate restTemplate = new RestTemplate();
    ArticleDTO articleDTO;


    @Override
    public ArticleDTO getArticleById(Long id) {
        return articleDTO = restTemplate.getForObject(BASE_URI + id, ArticleDTO.class);
    }

}
