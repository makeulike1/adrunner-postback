package com.gnm.adrunner.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class reqRemoteServer {

    public static ResponseEntity<String> requestGET(String URL){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("URL : "+URL);
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        System.out.println("RESPONSE : "+response);
        return response;
    }

}
