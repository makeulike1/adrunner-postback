package com.gnm.adrunner.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.gnm.adrunner.server.entity.Ads;
import com.gnm.adrunner.server.entity.AdsMedia;
import com.gnm.adrunner.server.entity.Media;
import com.gnm.adrunner.server.entity.Postback;
import com.gnm.adrunner.server.repo.MediaParamRepository;
import com.gnm.adrunner.server.repo.MediaRepository;
import com.gnm.adrunner.server.repo.PostbackRepository;
import com.gnm.adrunner.util.postbackURLBuilder;
import com.gnm.adrunner.util.reqRemoteServer;
import com.gnm.adrunner.util.timeBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PostbackAppNcpiService")
public class PostbackService {
    
    @Autowired
    AdsMediaService adsMediaService;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    MemoryDataService memoryDataService;
    
    @PersistenceContext
	private EntityManager entityManager;

    @Autowired 
    private PostbackRepository postbackRepository;

    @Autowired
    MediaParamRepository mediaParamRepository;

    @Transactional
    public Integer saveRecord(Postback p){
        entityManager.persist(p);
        return (int)(long)p.getId();
    }


    public Boolean isExistClickKey(String click_key){
        Integer id = postbackRepository.findIdByClickKey(click_key);

        if(id == null)
            return false;
        return true;
    }



    
    public Integer countTodayTotalPostbackByAdsKeyAndMediaKey(String adsKey, String mediaKey){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Postback> root = query.from(Postback.class);
        query.select(criteriaBuilder.count(root));

        List<Predicate> predicates = new ArrayList<Predicate>();        
        predicates.add(criteriaBuilder.equal(root.get("adsKey"), adsKey));
        predicates.add(criteriaBuilder.equal(root.get("mediaKey"), mediaKey));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(
            root.get("createtime"),     timeBuilder.getTodayEndTime()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(
            root.get("createtime"),  timeBuilder.getTodayStartTime()));
        query.where(predicates.toArray(new Predicate[]{}));

        return (int) (long) entityManager.createQuery(query).getSingleResult();
    }



    public Integer countTotalPostbackByAdsKey(String adsKey){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Postback> root = query.from(Postback.class);
        query.select(criteriaBuilder.count(root));
        query.where(criteriaBuilder.like(root.get("clickKey"), "%"+adsKey+":%"));
        return (int) (long) entityManager.createQuery(query).getSingleResult();
        
    }




    public Integer countPostbackByDate(String adsKey, String mediaKey, String startTime, String endTime){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Postback> root = query.from(Postback.class);
        query.select(criteriaBuilder.count(root));

        List<Predicate> predicates = new ArrayList<Predicate>();        
        predicates.add(criteriaBuilder.equal(root.get("adsKey"), adsKey));
        predicates.add(criteriaBuilder.equal(root.get("mediaKey"), mediaKey));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createtime"), endTime));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createtime"), startTime));
        query.where(predicates.toArray(new Predicate[]{}));

        return (int) (long) entityManager.createQuery(query).getSingleResult();
    }




    public Integer getSumAdvCostByDate(String adsKey, String mediaKey, String startTime, String endTime){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Postback> root = query.from(Postback.class);
        query.select(criteriaBuilder.sum(root.get("advCost")));
        query.groupBy(root.get("adsKey"));
        

        List<Predicate> predicates = new ArrayList<Predicate>();        
        predicates.add(criteriaBuilder.equal(root.get("adsKey"), adsKey));
        predicates.add(criteriaBuilder.equal(root.get("mediaKey"), mediaKey));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createtime"), endTime));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createtime"), startTime));
        query.where(predicates.toArray(new Predicate[]{}));

        Integer result = 0;

        try{
            Object tmp = entityManager.createQuery(query).getSingleResult();
            result = Integer.parseInt(tmp.toString());
        }catch(javax.persistence.NoResultException error){
            
        }
        
        return result;
    }



    public Integer getSumMediaCostByDate(String adsKey, String mediaKey, String startTime, String endTime){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Postback> root = query.from(Postback.class);
        query.select(criteriaBuilder.sum(root.get("mediaCost")));
        query.groupBy(root.get("adsKey"));
        

        List<Predicate> predicates = new ArrayList<Predicate>();        
        predicates.add(criteriaBuilder.equal(root.get("adsKey"), adsKey));
        predicates.add(criteriaBuilder.equal(root.get("mediaKey"), mediaKey));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createtime"), endTime));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createtime"), startTime));
        query.where(predicates.toArray(new Predicate[]{}));

        Integer result = 0;

        try{
            Object tmp = entityManager.createQuery(query).getSingleResult();
            result = Integer.parseInt(tmp.toString());
        }catch(javax.persistence.NoResultException error){
            
        }
        
        return result;
    }


    
    public void postbackHandler(AdsMedia am, String adsKey, String mediaKey, Postback p, String ptn_ck, Ads ads){
        
        
        // 전환 수가 데일리캡 한도에 다다를 경우 광고 상태가 중지로 변경됨
        Integer adsDayLimit     = am.getRunDailyCap();

        Integer todayTotalPostbackCount = countTodayTotalPostbackByAdsKeyAndMediaKey(adsKey, mediaKey);
 
        // 일일 광고 한도에 도달함
        if(todayTotalPostbackCount.compareTo(adsDayLimit) >= 0){        
            if(adsDayLimit != -1){
                adsMediaService.updateTodayLimit(true, adsKey, mediaKey);
                // 메모리 데이터 업데이트
                memoryDataService.updateMemoryData("ads-media", am.getId());
            }
        } 

 

        // 해당 광고가 포스트백 송수신이 설정되어있다면 매체사로 포스트백
        if(ads.getIsPostback()){
            Media m     = mediaRepository.findByKey(mediaKey);


            // 포스트백 연동이 된 매체사에 한해서만 포스트백을 전송
            if(m.getIsPostback()){
                try{

                    // 포스트백을 보낼 때는 매체사에서 받은 클릭키를 송신하도록
                    p.setClickKey(ptn_ck);

                    reqRemoteServer.requestGET(
                        postbackURLBuilder.build(
                            mediaParamRepository.findByTypeAndMediaKey(0, mediaKey), p, m.getPostbackInstall(), ads.getAff()));                
                }catch(Exception e){

                } 
            }

            m = null;
        }

        adsDayLimit = null;
        todayTotalPostbackCount = null;
    }
}
