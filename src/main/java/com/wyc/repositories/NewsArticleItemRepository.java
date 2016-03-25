package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.NewsArticleItem;

public interface NewsArticleItemRepository extends CrudRepository<NewsArticleItem, String>{

    Iterable<NewsArticleItem> findAllByArticleId(String articleId);

}
