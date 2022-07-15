package com.gnm.adrunner.server.repo;

import com.gnm.adrunner.server.entity.Postback;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostbackRepository extends CrudRepository<Postback, Integer> {

    @Query(value="SELECT id FROM postback WHERE ads_key=?1 and media_key=?2 and uuid=?3 and click_time=?4", nativeQuery = true)
    Integer findByAMUC(String adsKey, String mediaKey, String uuid, String clicktime);
 
}
