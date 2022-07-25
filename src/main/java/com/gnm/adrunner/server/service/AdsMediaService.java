package com.gnm.adrunner.server.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.gnm.adrunner.server.entity.AdsMedia;
import com.gnm.adrunner.server.repo.AdsMediaRepository;
import com.gnm.adrunner.server.repo.AdsRepository;
import com.gnm.adrunner.server.repo.MediaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdsMediaService")
public class AdsMediaService {

    @Autowired
    AdsMediaRepository adsMediaRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    LogAdsService logAdsService;

    @Autowired
    AdsRepository adsRepository;

    @PersistenceContext
	private EntityManager entityManager;

    @Transactional
    public Integer saveAdsMedia(AdsMedia adsMedia){
        entityManager.persist(adsMedia);
        return (int)(long)adsMedia.getId();
    }

    @Transactional
    public void updateTodayLimit(Boolean value, String adsKey, String mediaKey){
        adsMediaRepository.updateTodayLimit(value, adsKey, mediaKey);
    }
 
    public void deleteById(Integer amid) {
        adsMediaRepository.deleteById(amid);
    }

}
