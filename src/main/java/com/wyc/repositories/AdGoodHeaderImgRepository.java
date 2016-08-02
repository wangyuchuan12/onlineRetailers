package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.SystemAdGoodHeaderImg;

public interface AdGoodHeaderImgRepository extends CrudRepository<SystemAdGoodHeaderImg,String>{
    @Query("from com.wyc.domain.SystemAdGoodHeaderImg order by rank")
    Iterable<SystemAdGoodHeaderImg> findAllOrderByRankAsc();

	Iterable<SystemAdGoodHeaderImg> findAllByIdInOrderByRankAsc(String[] ids);

}
