package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.QuickEntrance;

public interface QuickEntranceRepository extends CrudRepository<QuickEntrance,String>{
    @Query("from com.wyc.domain.QuickEntrance order by rank")
    Iterable<QuickEntrance> findOrderByRankAsc();

}
