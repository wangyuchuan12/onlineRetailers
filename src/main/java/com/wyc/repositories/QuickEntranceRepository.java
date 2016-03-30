package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.SystemQuickEntrance;

public interface QuickEntranceRepository extends CrudRepository<SystemQuickEntrance,String>{
    @Query("from com.wyc.domain.SystemQuickEntrance order by rank")
    Iterable<SystemQuickEntrance> findOrderByRankAsc();

}
