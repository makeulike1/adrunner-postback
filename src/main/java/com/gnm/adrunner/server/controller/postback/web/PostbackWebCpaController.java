package com.gnm.adrunner.server.controller.postback.web;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import com.gnm.adrunner.config.GlobalConstant;
import com.gnm.adrunner.server.RequestResponseInterface;
import com.gnm.adrunner.server.entity.Ads;
import com.gnm.adrunner.server.entity.AdsMedia;
import com.gnm.adrunner.server.entity.Postback;
import com.gnm.adrunner.server.repo.PostbackRepository;
import com.gnm.adrunner.server.service.AdsMediaService;
import com.gnm.adrunner.server.service.AdsService;
import com.gnm.adrunner.server.service.PostbackService;
import com.gnm.adrunner.server.service.LogAdsService;
import com.gnm.adrunner.server.service.MemoryDataService;
import com.gnm.adrunner.server.repo.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.gnm.adrunner.server.repo.AdsMediaRepository;

import com.gnm.adrunner.util.redisUtil;
import com.gnm.adrunner.util.timeBuilder;  
 

// 웹 CPA 포스트백 처리
@Controller 
public class PostbackWebCpaController extends RequestResponseInterface{
 


    @Autowired
    PostbackService postbackService;

    @Autowired
    PostbackRepository postbackRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    AdsService  adsService;

    @Autowired
    LogAdsService logAdsService;

    @Autowired
    AdsMediaService adsMediaService;

    @Autowired
    AdsMediaRepository adsMediaRepository;

    @Autowired
    MemoryDataService memoryDataService;
    

    // 웹 CPA 포스트백
    @CrossOrigin(origins = "*")
    @GetMapping("/postback/web/cpa") 
    public ResponseEntity<String> webCpaPostback(
        @RequestParam(value="click_key",required = false) String ck,
        @RequestParam(value="user_agent", required = false) String userAgent,
        HttpServletRequest request){


        HttpHeaders responseHeaders = new HttpHeaders();



        // Request로부터 IP 가지고 옴
        String clientIP = request.getRemoteAddr();


        // User-Agent 파싱 
        String OS = "";
        String BRAND = "";
        String OS_VER = "";


        if(userAgent != null){
            String[] UA_TOKEN = userAgent.split(" ");


            for(int i=0;i<UA_TOKEN.length;i++){

                if(UA_TOKEN[i].toUpperCase().contains("IPHONE"))
                    OS = "iPhone";

                if(UA_TOKEN[i].toUpperCase().contains("ANDROID")){
                    OS = "Android";    
                    OS_VER = UA_TOKEN[i+1];
                }
            
                if(UA_TOKEN[i].toUpperCase().contains("SM-"))
                    BRAND = "Samsung";

                if(UA_TOKEN[i].toUpperCase().contains("LG-"))
                    BRAND = "LG";
                    
                if(UA_TOKEN[i].toUpperCase().contains("MACINTOSH"))
                    OS = "Macintosh";

                if(UA_TOKEN[i].toUpperCase().contains("WINDOWS"))
                    OS = "Windows";
            }
        }

 

        // 파라미터에 클릭키가 존재하지 않는 경우 206 에러
        if(ck == null)
            return ResponseEntity.status(206)
                .headers(responseHeaders)
                .body(getStatusMessage(206));

      


        String[] token  = ck.split(":");


        if(token.length != 14){
            // 유효하지 않는 클릭키 214 에러
            return ResponseEntity.status(214)
                .headers(responseHeaders)
                .body(getStatusMessage(214));
        }

        

                
        // 광고키에 해당하는 광고를 메모리 데이터로부터 READ
        Ads ads = adsRepository.findByAdsKey(token[0]);






        // 존재하지 않는 광고의 경우 208 에러
        if(ads == null)
            return ResponseEntity.status(208)
                .headers(responseHeaders)
                .body(RequestResponseInterface.getStatusMessage(208));
    





        // 클릭키가 존재하지 않는 경우 214 에러
        if(!redisUtil.findck(token[0], ck, ads.getRedisIndex())){
            return ResponseEntity.status(214)
                .headers(responseHeaders)
                .body(getStatusMessage(214));
        }




        // 광고키와 매체사 키에 해당하는 광고 매체사 연동 정보 조회
        AdsMedia am = adsMediaRepository.findByAdsKeyAndMediakey(token[0], token[1]);
                
            
 

        

        // 광고가 앱 NCPI 광고가 아닌 경우 222 에러 처리
        if(ads.getType() != GlobalConstant.ADS_TYPE_WEB_CPA)
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
        if(ads.getIsDailyCap() && am.getIsDayLimit())
                return ResponseEntity.status(216)
                        .headers(responseHeaders)
                        .body(RequestResponseInterface.getStatusMessage(216));
   



        Integer mediaCost = adsMediaRepository.getMediaCostByAdsKeyAndMediaKey(token[0], token[1]);

        Postback p = new Postback();
        p.setCreatetime(timeBuilder.getCurrentTime());
        p.setIp(clientIP);
        p.setEventName("");
        p.setEventValue("");
        p.setLanguage("");
        p.setNetwork("");
        p.setCarrier("");
        p.setBrand(BRAND);
        p.setModel("");
        p.setOs(OS);
        p.setOsVer(OS_VER);
        p.setCountry("");
        p.setDeviceId("");
        p.setEventTime(timeBuilder.getCurrentTime());
        p.setAdvCost(ads.getCost2());
        p.setMediaCost(mediaCost);



        // 클릭키에서 데이터 구분
        p.setAdsKey(token[0]);
        p.setMediaKey(token[1]);
        p.setClickTime(token[2]);
        p.setUuid(token[3]);
        p.setMediaKey(token[4]);
        p.setPtnPub(token[5]);
        p.setSubPub(token[6]);
        p.setGaid(token[7]);
        p.setIdfa(token[8]);
        p.setS_p1(token[9]);
        p.setS_p2(token[10]);
        p.setS_p3(token[11]);
        p.setS_p4(token[12]);
        p.setS_p5(token[13]);


        // 이미 사전에 포스트백을 받은 적이 있는 경우 207 에러
        if(postbackService.isExistClickKey(token[0], token[1], token[3], token[2]))
            return ResponseEntity.status(207)
                .headers(responseHeaders)
                .body(getStatusMessage(207));


        postbackRepository.save(p);
 

        // 광고 한도 체크 및 매체사 송신이 필요할 경우 매체사로 송신
        postbackService.postbackHandler(am, token[0], token[1], p, ads, ck);
 
        
        p = null;
        token = null;


        return ResponseEntity.status(200)
                .headers(responseHeaders)
                .body(getStatusMessage(200));
    }



}
