package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.GroupPartake;

public interface GroupPartakeRepository extends CrudRepository<GroupPartake, String>{

    public Iterable<GroupPartake> findByCustomerid(String customerId);

    public Iterable<GroupPartake> findAllByGroupIdOrderByRoleAsc(String id);

    public GroupPartake findByCustomeridAndGroupId(String customerId,
            String groupId);
    @Query("select count(*) from com.wyc.domain.GroupPartake g where g.groupId = :groupId")
    public int countByGroupId(@Param("groupId")String groupId);

    public Iterable<GroupPartake> findAllByGroupIdOrderByDateTimeAsc(String id);

	public GroupPartake findByCustomeridAndOrderId(String customerId, String orderId);

    public Iterable<GroupPartake> findAllByCustomeridOrderByDateTimeDesc(
            String customerId);

}
