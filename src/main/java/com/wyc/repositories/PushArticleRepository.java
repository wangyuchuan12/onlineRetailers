package com.wyc.repositories;

import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.PushArticle;

public interface PushArticleRepository extends CrudRepository<PushArticle, String>{

    Iterable<PushArticle> findAllByStatusAndPushTimeLessThan(
            int notSentStatus, DateTime dateTime);

}
