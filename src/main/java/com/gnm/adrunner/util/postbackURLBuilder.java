package com.gnm.adrunner.util;

import com.gnm.adrunner.server.entity.MediaParam;
import com.gnm.adrunner.server.entity.Postback;

public class postbackURLBuilder {


    public static String build(Iterable<MediaParam> list, Postback p, String url, Integer affId){


        String token [] = p.getClickKey().split(":");

        for(String e : token){
            System.out.println(e);
        }

        for(MediaParam mp : list){
            switch(mp.getParamValue()){
                case "click_key":   url = url.replace(mp.getParamKey(),   token[4]);        break;
                case "ptn_pub":     url = url.replace(mp.getParamKey(),   token[5]);     break;
                case "sub_pub":     url = url.replace(mp.getParamKey(),   token[6]);     break;
                case "gaid":        url = url.replace(mp.getParamKey(),   token[7]);       break;
                case "idfa":        url = url.replace(mp.getParamKey(),   p.getIdfa());       break;
                case "ip":          url = url.replace(mp.getParamKey(),   p.getIp());         break;
                case "brand":       url = url.replace(mp.getParamKey(),   p.getBrand());      break;
                case "model":       url = url.replace(mp.getParamKey(),   p.getModel());      break;
                case "os":          url = url.replace(mp.getParamKey(),   p.getOs());         break;
                case "os_ver":      url = url.replace(mp.getParamKey(),   p.getOsVer());      break;
                case "country":     url = url.replace(mp.getParamKey(),   p.getCountry());    break;
                case "carrier":     url = url.replace(mp.getParamKey(),   p.getCarrier());    break;
                case "s_p1":        url = url.replace(mp.getParamKey(),   token[9]);        break;
                case "s_p2":        url = url.replace(mp.getParamKey(),   token[10]);        break;
                case "s_p3":        url = url.replace(mp.getParamKey(),   token[11]);        break;
                case "s_p4":        url = url.replace(mp.getParamKey(),   token[12]);        break;
                case "s_p5":        url = url.replace(mp.getParamKey(),   token[13]);        break;
                case "aff":         url = url.replace(mp.getParamKey(),   affId.toString());  break;
                case "event_value": url = url.replace(mp.getParamKey(),   p.getEventValue());   break;
            }
        }

        token = null;
        
        System.out.println(url);

        return url;
    }
}
