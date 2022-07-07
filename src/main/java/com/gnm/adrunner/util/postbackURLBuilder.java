package com.gnm.adrunner.util;

import com.gnm.adrunner.server.entity.MediaParam;
import com.gnm.adrunner.server.entity.Postback;

public class postbackURLBuilder {


    public static String build(Iterable<MediaParam> list, Postback p, String url, Integer affId){

        for(MediaParam mp : list){
            switch(mp.getParamValue()){
                case "click_key":   url = url.replace(mp.getParamKey(),   p.getClickKey());   break;
                case "ptn_pub":     url = url.replace(mp.getParamKey(),   p.getPtnPub());     break;
                case "sub_pub":     url = url.replace(mp.getParamKey(),   p.getSubPub());     break;
                case "gaid":        url = url.replace(mp.getParamKey(),   p.getGaid());       break;
                case "idfa":        url = url.replace(mp.getParamKey(),   p.getIdfa());       break;
                case "ip":          url = url.replace(mp.getParamKey(),   p.getIp());         break;
                case "brand":       url = url.replace(mp.getParamKey(),   p.getBrand());      break;
                case "model":       url = url.replace(mp.getParamKey(),   p.getModel());      break;
                case "os":          url = url.replace(mp.getParamKey(),   p.getOs());         break;
                case "os_ver":      url = url.replace(mp.getParamKey(),   p.getOsVer());      break;
                case "country":     url = url.replace(mp.getParamKey(),   p.getCountry());    break;
                case "carrier":     url = url.replace(mp.getParamKey(),   p.getCarrier());    break;
                case "s_p1":        url = url.replace(mp.getParamKey(),   p.getsP1());        break;
                case "s_p2":        url = url.replace(mp.getParamKey(),   p.getsP2());        break;
                case "s_p3":        url = url.replace(mp.getParamKey(),   p.getsP3());        break;
                case "s_p4":        url = url.replace(mp.getParamKey(),   p.getsP4());        break;
                case "s_p5":        url = url.replace(mp.getParamKey(),   p.getsP5());        break;
                case "aff":         url = url.replace(mp.getParamKey(),   affId.toString());  break;
            }
        }
        
        return url;
    }
}
