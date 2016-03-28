package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.AdGoodHeaderImg;

public interface AdGoodHeaderImgRepository extends CrudRepository<AdGoodHeaderImg,String>{

    Iterable<AdGoodHeaderImg> findAllByGoodTypeId(String goodTypeId);

}
