package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.LuckDrawGood;

public interface LuckDrawGoodRepository extends CrudRepository<LuckDrawGood, String>{

    LuckDrawGood findByRecordIndex(int recordIndex);

}
