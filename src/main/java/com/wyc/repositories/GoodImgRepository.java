package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodImg;

public interface GoodImgRepository extends CrudRepository<GoodImg, String>{

    public List<GoodImg> findAllByGoodIdOrderByLevelDesc(String goodId);

}
