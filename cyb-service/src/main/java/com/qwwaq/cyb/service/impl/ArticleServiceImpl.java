package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.service.api.ArticleService;
import com.qwwaq.cyb.service.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Override
    public Integer createArticle(Article article) {
        return articleMapper.insertArticle(article);
    }

    @Override
    public Integer update(Article article) {
        return articleMapper.updateArticle(article);
    }



    @Override
    public List<Article> queryArticleWithModule(String module) {
        return articleMapper.queryArticleWithModule(module);
    }

    @Override
    public Integer removeArticle(Integer id) {
        return articleMapper.removeArticle(id);
    }
}
