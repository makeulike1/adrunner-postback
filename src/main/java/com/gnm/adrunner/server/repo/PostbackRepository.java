package com.gnm.adrunner.server.repo;

import com.gnm.adrunner.server.entity.Postback;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostbackRepository extends CrudRepository<Postback, Integer> {

    @Query(value="", nativeQuery = true)
    Integer findByAMUC(String adsKey, String mediaKey, String uuid, String clicktime);
 
}
