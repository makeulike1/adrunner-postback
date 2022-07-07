package com.gnm.adrunner.server.repo;

import com.gnm.adrunner.server.entity.MediaParam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MediaParamRepository extends CrudRepository<MediaParam, Integer>{

    @Query(value="select * FROM media_param where type=?1 and media_key=?2", nativeQuery = true)
    Iterable<MediaParam> findByTypeAndMediaKey(int type, String mediaKey);
}
