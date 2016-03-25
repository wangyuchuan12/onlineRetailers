package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.PushArticle;
import com.wyc.repositories.PushArticleRepository;

@Service
public class PushArticleService {
    @Autowired
    private PushArticleRepository pushArticleRepository;

    public Iterable<PushArticle> findAllByStatusAndPushTimeLessThan(
            int notSentStatus, DateTime dateTime) {
        return pushArticleRepository.findAllByStatusAndPushTimeLessThan(notSentStatus,dateTime);
    }

	public PushArticle add(PushArticle pushArticle) {
		pushArticle.setCreateAt(new DateTime());
		pushArticle.setUpdateAt(new DateTime());
		pushArticle.setId(UUID.randomUUID().toString());
		return pushArticleRepository.save(pushArticle);
	}
}
