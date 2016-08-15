package com.wyc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.Good;

public interface GoodRepository extends CrudRepository<Good, String>{

    Iterable<Good> findAllByStatusOrderByRankAsc(int status);

    Iterable<Good> findAllByStatusAndGoodTypeOrderByRankAsc(int status,
            String goodTypeId);

    Iterable<Good> findAllByGoodTypeAndIsDisplayMainOrderByRankAsc(
            String goodTypeId, boolean b);

    @Query(nativeQuery=true,value="select g.* from good g where g.good_type=:goodTypeId and g.is_display_main=:isDisplayMain union all select c.* from good c where c.id in (:goods) order by rank asc")
	Iterable<Good> findAllByGoodTypeAndUnionIdsAndIsDisplayMainOrderByRank(@Param("goodTypeId")String goodTypeId,@Param("goods")String[] goods,
			@Param("isDisplayMain")boolean b);
}
