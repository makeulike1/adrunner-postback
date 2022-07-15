package com.gnm.adrunner.util;
 

import java.util.UUID;

public class keyBuilder {


    // UUID 생성
    public static String buildUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
  

    // min <= (x) <max 사이의 랜덤 값 생성 */
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    // 매체사 키, 광고주 키 발급
    public static String buildIdentifier(Integer Id){
        return Id.toString() + keyBuilder.buildUUID().substring(0, 5).toUpperCase();
    }
}
