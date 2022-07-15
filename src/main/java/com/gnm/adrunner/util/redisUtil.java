package com.gnm.adrunner.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.gnm.adrunner.config.GlobalConstant;
import com.gnm.adrunner.config.RedisConfig;


public class redisUtil {

    
    // Redis 리스트 사이즈 반환
    public static Integer getListSize(String ckListId, Integer redisIndex){
        Integer result = 0;

        for(int i=0;i<GlobalConstant.NUMBER_OF_REDIS;i++){
            Integer tmp = 0;
            Object clkKeyListSize       = RedisConfig.redisConn.get(i).get(redisIndex).opsForList().size(ckListId);
            if(clkKeyListSize == null)
                tmp = 0;
            else tmp = Integer.parseInt(clkKeyListSize.toString());
            result += tmp;
        }

        return result;
    }
    




    

    // Redis 클릭수 반환
    public static Integer getCkCount(String adsKey, Integer redisIndex){

        Integer totalCkCount = 0;
        for(int i=0;i<GlobalConstant.NUMBER_OF_REDIS;i++){
            Set<String> redisKeys = RedisConfig.redisConn.get(i).get(redisIndex).keys(adsKey+"*");
            List<String> keysList = new ArrayList<>(); 
            Iterator<String> it = redisKeys.iterator();
            while (it.hasNext()) {
                keysList.add(it.next());
            }

            
            Integer clickCount = 0;
            for(String key : keysList){
                Object tmp = RedisConfig.redisConn.get(i).get(redisIndex).opsForList().size(key);
                if(tmp != null)
                    clickCount += Integer.parseInt(tmp.toString());
            }

            totalCkCount += clickCount;
        }

        return totalCkCount;
    }

 

    // Redis에서 클릭키 검색
    public static Boolean findck(String adsKey, String ck, Integer redisIndex){

        for(int i=0;i<GlobalConstant.NUMBER_OF_REDIS;i++){
            Set<String> redisKeys = RedisConfig.redisConn.get(i).get(redisIndex).keys(adsKey+"*");
            List<String> keysList = new ArrayList<>(); 
            Iterator<String> it = redisKeys.iterator();
            while (it.hasNext()) {
                keysList.add(it.next());
            }

            for(String listKey : keysList){
                Object tmp = RedisConfig.redisConn.get(i).get(redisIndex).opsForList().indexOf(listKey, ck);
                if(tmp != null)
                    return true;
            }
        }

        
        return false;
    }
 



}
