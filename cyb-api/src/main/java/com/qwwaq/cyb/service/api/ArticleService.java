package com.qwwaq.cyb.service.api;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;

import java.util.List;

public interface ArticleService {
    Integer createArticle(Article article);
    Integer update(Article article);
    List<Article> queryArticleWithModule(String module,Integer userId);
    Article queryArticleDetail(Integer id,Integer userId);
    List<Article> listArticlesWithCreator(Integer userId);  //查看我的文章
    List<Article> listArticlesWithFollower(Integer userId);  //查看我关注的文章


    Integer removeArticle(Integer id);
}
