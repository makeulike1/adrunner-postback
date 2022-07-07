package com.gnm.adrunner.server.controller.postback.app.cpa;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import com.gnm.adrunner.config.GlobalConstant;
import com.gnm.adrunner.server.RequestResponseInterface;
import com.gnm.adrunner.server.entity.Ads;
import com.gnm.adrunner.server.entity.AdsMedia;
import com.gnm.adrunner.server.entity.Media;
import com.gnm.adrunner.server.entity.Postback;
import com.gnm.adrunner.server.service.AdsMediaService;
import com.gnm.adrunner.server.service.AdsService;
import com.gnm.adrunner.server.service.PostbackService;
import com.gnm.adrunner.server.service.LogAdsService;
import com.gnm.adrunner.server.service.MemoryDataService;
import com.gnm.adrunner.util.postbackURLBuilder;
import com.gnm.adrunner.util.redisUtil;
import com.gnm.adrunner.util.reqRemoteServer;
import com.gnm.adrunner.util.timeBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.gnm.adrunner.server.repo.AdminLoginRepository;
import com.gnm.adrunner.server.repo.AdsMediaRepository;
import com.gnm.adrunner.server.repo.AdsRepository;
import com.gnm.adrunner.server.repo.MediaParamRepository;
import com.gnm.adrunner.server.repo.MediaRepository;

import java.net.URLDecoder;



// 앱 CPA 포스트백 처리
@Controller 
public class PostbackAppCpaController extends RequestResponseInterface{

 
    @Autowired
    PostbackService postbackService;
    
    @Autowired
    AdsService adsService;

    @Autowired
    LogAdsService logAdsService;
    
    @Autowired
    AdminLoginRepository adminLoginRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    AdsMediaRepository adsMediaRepository;

    @Autowired
    AdsMediaService adsMediaService;

    @Autowired
    MemoryDataService memoryDataService;
    
    @Autowired
    MediaRepository mediaRepository;


    @Autowired
    MediaParamRepository mediaParamRepository;


    
    // 앱 CPA 포스트백
    @GetMapping("/postback/app/cpa") 
    public @ResponseBody ResponseEntity<String> appCPAPostback(
        @RequestParam(value="click_key",required = false, defaultValue = "") String ck,
        @RequestParam(value="ptn_pub",required = false, defaultValue = "") String ptn_pub,
        @RequestParam(value="sub_pub",required = false, defaultValue = "") String sub_pub,
        @RequestParam(value="os",required = false, defaultValue = "") String os,
        @RequestParam(value="android_id",required = false, defaultValue = "") String androidId,
        @RequestParam(value="device_id",required = false, defaultValue = "") String deviceId,
        @RequestParam(value="gaid",required = false, defaultValue = "") String gaid,
        @RequestParam(value="idfa",required = false, defaultValue = "") String idfa,
        @RequestParam(value="carrier",required = false, defaultValue = "") String carrier,
        @RequestParam(value="brand",required = false, defaultValue = "") String brand,
        @RequestParam(value="model",required = false, defaultValue = "") String model,
        @RequestParam(value="ip",required = false) String ip,
        @RequestParam(value="os_ver",required = false, defaultValue = "") String os_ver,
        @RequestParam(value="country",required = false, defaultValue = "") String country,
        @RequestParam(value="language",required = false, defaultValue = "") String language,
        @RequestParam(value="network",required = false, defaultValue = "") String network,
        @RequestParam(value="event_name",required = false, defaultValue = "") String eventName,
        @RequestParam(value="event_value",required = false, defaultValue = "") String eventValue,
        @RequestParam(value="event_time",required = false, defaultValue = "") String eventTime,
        @RequestParam(value="s_p1", required = false, defaultValue = "") String sP1,
        @RequestParam(value="s_p2", required = false, defaultValue = "") String sP2,
        @RequestParam(value="s_p3", required = false, defaultValue = "") String sP3,
        @RequestParam(value="s_p4", required = false, defaultValue = "") String sP4,
        @RequestParam(value="s_p5", required = false, defaultValue = "") String sP5,
        HttpServletRequest request) throws UnsupportedEncodingException{



            HttpHeaders responseHeaders = new HttpHeaders();

            String DECODED_EVENT_TIME = URLDecoder.decode(eventTime, StandardCharsets.UTF_8.name());
        

            
            // 이벤트 시간이 제대로 URL 인코딩 되지 않았을 때 218 에러
            if(!timeBuilder.isValidDateTime(DECODED_EVENT_TIME)){
                return ResponseEntity.status(218)
                    .headers(responseHeaders)
                    .body(getStatusMessage(218));
            }

            

 
            // 파라미터에 클릭키가 존재하지 않는 경우 206 에러
            if(ck == null)
                return ResponseEntity.status(206)
                    .headers(responseHeaders)
                    .body(getStatusMessage(206));

                    
    
            // 이미 사전에 포스트백을 받은 적이 있는 경우 207 에러
            if(postbackService.isExistClickKey(ck))
                return ResponseEntity.status(207)
                    .headers(responseHeaders)
                    .body(getStatusMessage(207));
 
                




            // 이벤트 이름이 제대로 들어오지 않은 경우 223 에러
            if(eventName == null)
                return ResponseEntity.status(223)
                    .headers(responseHeaders)
                    .body(getStatusMessage(223));


                    


            // 이벤트 값이 제대로 들어오지 않은 경우 224 에러
            if(eventValue == null)
                return ResponseEntity.status(224)
                    .headers(responseHeaders)
                    .body(getStatusMessage(224));




            // 이벤트 시간이 제대로 들어오지 않은 경우 225 에러
            if(eventTime == null)
                return ResponseEntity.status(225)
                    .headers(responseHeaders)
                    .body(getStatusMessage(225));



            String adsKey   = ck.split(":")[0];

            String mediaKey = ck.split(":")[1];
    



            

            // 광고키에 해당하는 광고를 메모리 데이터로부터 READ
            Ads ads = adsRepository.findByAdsKey(adsKey);





            // 존재하지 않는 광고의 경우 208 에러
            if(ads == null)
              return ResponseEntity.status(208)
                  .headers(responseHeaders)
                  .body(RequestResponseInterface.getStatusMessage(208));







            // 클릭키가 존재하지 않는 경우 214 에러
            if(!redisUtil.findck(adsKey, ck, ads.getRedisIndex())){
                return ResponseEntity.status(214)
                        .headers(responseHeaders)
                        .body(getStatusMessage(214));
            }
    



            // 광고키와 매체사 키에 해당하는 광고 매체사 연동 정보 조회
            AdsMedia am = adsMediaRepository.findByAdsKeyAndMediakey(adsKey, mediaKey);


 


            // 광고가 앱 CPA 광고가 아닌 경우 222 에러 처리
            if(ads.getType() != GlobalConstant.ADS_TYPE_APP_CPA)
                return ResponseEntity.status(222)
                    .headers(responseHeaders)
                    .body(getStatusMessage(222));



            
            // 일시 중지된 광고인 경우 211 에러
            if(ads.getStatus() == GlobalConstant.ADS_STATUS_PAUSE)
                return ResponseEntity.status(211)
                        .headers(responseHeaders)
                        .body(RequestResponseInterface.getStatusMessage(211));






            // 종료된 광고인 경우 226 에러
            if(ads.getStatus() == GlobalConstant.ADS_STATUS_DISMISS)
                return ResponseEntity.status(226)
                        .headers(responseHeaders)
                        .body(RequestResponseInterface.getStatusMessage(226));



 


            // 광고가 일일 한도에 도달하였을 경우 
            if(am.getIsDayLimit() == true)
                return ResponseEntity.status(216)
                        .headers(responseHeaders)
                        .body(RequestResponseInterface.getStatusMessage(216));

                        




            // 매체사 비용 
            Integer mediaCost = am.getMediaCost();


            Postback p = new Postback();
            p.setClickKey(ck);
            p.setDeviceId(deviceId);
            p.setGaid(gaid);
            p.setIdfa(idfa);
            p.setAdsKey(adsKey);
            p.setMediaKey(mediaKey);
            p.setCarrier(carrier);
            p.setBrand(brand);
            p.setModel(model);
            p.setOs(os);
            p.setOsVer(os_ver);
            p.setIp(ip);
            p.setCountry(country);
            p.setLanguage(language);
            p.setNetwork(network);
            p.setPtnPub(ptn_pub);
            p.setSubPub(sub_pub);
            p.setEventName(eventName);
            p.setEventValue(eventValue);
            p.setEventTime(eventTime);
            p.setAdvCost(ads.getCost2());
            p.setMediaCost(mediaCost);
            p.setS_p1(sP1);
            p.setS_p2(sP2);
            p.setS_p3(sP3);
            p.setS_p4(sP4);
            p.setS_p5(sP5);
            p.setCreatetime(timeBuilder.getCurrentTime());
            
            
            
    


     
            // 포스트백 테이블에 포스트백 로그 삽입
            postbackService.saveRecord(p);

            
            // 전환 수가 데일리캡 한도에 다다를 경우 광고 상태가 중지로 변경됨
            Integer adsDayLimit     = am.getMediaDailyCap();

            Integer todayTotalPostbackCount = postbackService.countTodayTotalPostbackByAdsKeyAndMediaKey(adsKey, mediaKey);


            // 일일 광고 한도에 도달함
            if(todayTotalPostbackCount.compareTo(adsDayLimit) >= 0){              
                if(adsDayLimit != -1){
                    adsMediaService.updateIsDayLimit(true, adsKey, mediaKey);
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
                        reqRemoteServer.requestGET(
                            postbackURLBuilder.build(mediaParamRepository.findByTypeAndMediaKey(1, mediaKey), p, m.getPostbackEvent(), ads.getAff()));
                    }catch(Exception e){

                    }
                    
                }

                m = null;
            }




            p           =   null;
            adsKey      =   null;
            mediaKey    =   null;
            ads         =   null;
            todayTotalPostbackCount = null;
        
                
        return ResponseEntity.status(200)
            .headers(responseHeaders)
            .body(getStatusMessage(200));
    }



    
}
