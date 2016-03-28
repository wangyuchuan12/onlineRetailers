package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.TextArticleItem;
import com.wyc.repositories.TextArticleItemRepository;

@Service
public class TextArticleItemService {
    @Autowired
    private TextArticleItemRepository textArticleItemRepository;

    public TextArticleItem findByArticleId(String articleId) {
        return textArticleItemRepository.findByArticleId(articleId);
    }
}
