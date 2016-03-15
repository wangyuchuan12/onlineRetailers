package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.repositories.GroupPartakeDeliverRespository;

@Service
public class GroupPartakeDeliverService {
    @Autowired
    private GroupPartakeDeliverRespository groupPartakeDeliverRespository;

    public GroupPartakeDeliver findByGroupPartakeId(String groupPartakeId) {
        return groupPartakeDeliverRespository.findByGroupPartakeId(groupPartakeId);
    }

    public GroupPartakeDeliver add(GroupPartakeDeliver groupPartakeDeliver) {
        groupPartakeDeliver.setCreateAt(new DateTime());
        groupPartakeDeliver.setUpdateAt(new DateTime());
        groupPartakeDeliver = groupPartakeDeliverRespository.save(groupPartakeDeliver);
        return groupPartakeDeliver;
    }
}
