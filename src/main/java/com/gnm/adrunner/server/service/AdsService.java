package com.gnm.adrunner.server.service;

 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import com.gnm.adrunner.server.entity.Ads;
import com.gnm.adrunner.server.repo.AdsRepository;
import com.gnm.adrunner.server.repo.AffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdsService")
public class AdsService {
 

    @PersistenceContext
	private EntityManager entityManager;

    @Autowired
    PostbackService postbackService;

    @Autowired
    AffService affService;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    AffRepository affRepository;
 


    @Transactional
    public void updateAdsByStatus(Integer adsId, Integer status){
        // 광고 식별자에 대해서 광고 상태 변경
        adsRepository.updateAdsByStatus(adsId, status);
    }

    
    public Ads findById(Integer adsId) {
        return adsRepository.findByID(adsId);
    }

    public Ads findByAdsKey(String adsKey) {
        return adsRepository.findByAdsKey(adsKey);
    }
 

    public Iterable<Integer> getIdList() {
        return adsRepository.listAllId();
    }



    public Integer getAdTypeByAdsKey(String adsKey) {
        return adsRepository.getAdType(adsKey);
    }
 
}
