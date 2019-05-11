package com.qwwaq.cyb.service.api;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.User;

import java.util.List;

public interface ArticleService {
    Integer createArticle(Article article);
    Integer update(Article article);
    List<Article> queryArticleWithModule(String module);
    Integer removeArticle(Integer id);
}
