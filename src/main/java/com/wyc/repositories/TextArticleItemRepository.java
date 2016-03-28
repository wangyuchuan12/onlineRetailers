package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.TextArticleItem;

public interface TextArticleItemRepository extends CrudRepository<TextArticleItem, String>{

    TextArticleItem findByArticleId(String articleId);

}
