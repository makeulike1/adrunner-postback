package com.gnm.adrunner.server.controller.postback.app.ncpi;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import com.gnm.adrunner.util.redisUtil;
import com.gnm.adrunner.util.timeBuilder;
import com.gnm.adrunner.server.RequestResponseInterface;
import com.gnm.adrunner.server.entity.Postback;
import com.gnm.adrunner.server.repo.PostbackRepository;
import com.gnm.adrunner.server.service.AdsMediaService;
import com.gnm.adrunner.server.service.AdsService;
import com.gnm.adrunner.server.service.PostbackService;
import com.gnm.adrunner.server.service.LogAdsService;
import com.gnm.adrunner.server.service.MemoryDataService;
import com.gnm.adrunner.config.GlobalConstant;
import com.gnm.adrunner.server.entity.Ads;
import com.gnm.adrunner.server.entity.AdsMedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.gnm.adrunner.server.repo.AdsRepository;
import com.gnm.adrunner.server.repo.MediaRepository;
import com.gnm.adrunner.server.repo.AdsMediaRepository;

// 포스트백 처리
@Controller 
public class PostbackAppNcpiController extends RequestResponseInterface{
 


    @Autowired 
    PostbackService postbackService;
    
    @Autowired
    PostbackRepository postbackRepository;
 
    @Autowired
    AdsService adsService;

    @Autowired
    LogAdsService logAdsService;

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


    // 앱 NCPI 포스트백
    @GetMapping("/postback/app/ncpi") 
    public @ResponseBody ResponseEntity<String> NCPIpostback(
        @RequestParam(value="click_key",required = false, defaultValue = "") String ck,
        @RequestParam(value="device_id",required = false, defaultValue = "") String deviceId,
        @RequestParam(value="gaid",required = false, defaultValue = "") String gaid,
        @RequestParam(value="idfa",required = false, defaultValue = "") String idfa,
        @RequestParam(value="carrier",required = false, defaultValue = "") String carrier,
        @RequestParam(value="brand",required = false, defaultValue = "") String brand,
        @RequestParam(value="model",required = false, defaultValue = "") String model,
        @RequestParam(value="os",required = false, defaultValue = "") String os,
        @RequestParam(value="os_ver",required = false, defaultValue = "") String os_ver,
        @RequestParam(value="ip", required = false, defaultValue = "") String ip,
        @RequestParam(value="country", required = false, defaultValue = "") String country,
        @RequestParam(value="language", required = false, defaultValue = "") String language,
        @RequestParam(value="network", required = false, defaultValue = "") String network,
        @RequestParam(value="ptn_pub", required = false, defaultValue = "") String ptn_pub,
        @RequestParam(value="sub_pub", required = false, defaultValue = "") String sub_pub,
        @RequestParam(value="s_p1", required = false, defaultValue = "") String sP1,
        @RequestParam(value="s_p2", required = false, defaultValue = "") String sP2,
        @RequestParam(value="s_p3", required = false, defaultValue = "") String sP3,
        @RequestParam(value="s_p4", required = false, defaultValue = "") String sP4,
        @RequestParam(value="s_p5", required = false, defaultValue = "") String sP5,
        HttpServletRequest request){

    

        HttpHeaders responseHeaders = new HttpHeaders();


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



        String[] token  = ck.split(":");


        if(token.length != 3){
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
        if(ads.getType() != GlobalConstant.ADS_TYPE_APP_NCPI)
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
        if(ads.getIsDailyCap() && am.getTodayLimit())
            return ResponseEntity.status(216)
                    .headers(responseHeaders)
                    .body(RequestResponseInterface.getStatusMessage(216));

                

        Integer mediaCost = adsMediaRepository.getMediaCostByAdsKeyAndMediaKey(token[0], token[1]);

        Postback p = new Postback();
        p.setClickKey(ck);
        p.setAdsKey(token[0]);
        p.setMediaKey(token[1]);
        p.setDeviceId(deviceId);
        p.setGaid(gaid);
        p.setIdfa(idfa);
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
        p.setCreatetime(timeBuilder.getCurrentTime());
        p.setEventName("");
        p.setEventValue("");
        p.setEventTime("1111-11-11 11:11:11");
        p.setAdvCost(ads.getCost2());
        p.setMediaCost(mediaCost);
        p.setS_p1(sP1);
        p.setS_p2(sP2);
        p.setS_p3(sP3);
        p.setS_p4(sP4);
        p.setS_p5(sP5);
         

        // 포스트백 테이블에 포스트백 로그 삽입
        postbackService.saveRecord(p);


        // 광고 한도 체크 및 매체사 송신이 필요할 경우 매체사로 송신
        postbackService.postbackHandler(am, token[0], token[1], p, token[2], ads);


        p = null;
        token = null;
        

        return ResponseEntity.status(200)
                .headers(responseHeaders)
                .body(getStatusMessage(200));
    }

    
}
