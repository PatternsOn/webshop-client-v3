package com.patternson.webshopclient.service;

import com.patternson.webshopclient.model.ArticleDTO;

/**
 *
 * Created by Tobias Pettersson 20180321
 */
public interface ArticleService {

    ArticleDTO getArticleById(Long id);

}
