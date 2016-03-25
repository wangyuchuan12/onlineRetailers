package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.NewsArticleItem;
import com.wyc.repositories.NewsArticleItemRepository;

@Service
public class NewsArticleItemService {
    @Autowired
    private NewsArticleItemRepository newsArticleItemRepository;

    public Iterable<NewsArticleItem> findAllByArticleId(String articleId) {
        return newsArticleItemRepository.findAllByArticleId(articleId);
    }

	public NewsArticleItem add(NewsArticleItem newsArticleItem) {
		newsArticleItem.setUpdateAt(new DateTime());
		newsArticleItem.setCreateAt(new DateTime());
		newsArticleItem.setId(UUID.randomUUID().toString());
		return newsArticleItemRepository.save(newsArticleItem);
	}
}
