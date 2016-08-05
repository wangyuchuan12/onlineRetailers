package com.wyc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.GoodGroup;

public interface GoodGroupRepository extends CrudRepository<GoodGroup, String>{
    @Query("select g from com.wyc.domain.GoodGroup g where g.createAt = (select max(createAt) from com.wyc.domain.GoodGroup)")
    public GoodGroup selectLastestGoodGroup();
    
    @Query("select g from com.wyc.domain.GoodGroup g where g.createAt = (select max(g2.createAt) from com.wyc.domain.GoodGroup g2 where g2.groupHead=:groupHead)")
    public GoodGroup selectLastestGoodGroupByGroupHead(@Param("groupHead") String groupHead);


    public Iterable<GoodGroup> findAllByIdInOrderByCreateAtDesc(
            List<String> groupIds);

    @Query("select g from com.wyc.domain.GoodGroup g where g.createAt in (select min(g2.createAt) from com.wyc.domain.GoodGroup g2 where g2.goodId=:goodId and g2.result=:result)")
	public List<GoodGroup> findEarliestGroupByGoodAndResult(@Param("goodId") String goodId,@Param("result") int result);
    
    @Query("select a from com.wyc.domain.GoodGroup a , com.wyc.domain.HotGroup b where b.groupId = a.id and b.status=0 and a.result = 1")
	public List<GoodGroup> findAllHotGroups();
}
