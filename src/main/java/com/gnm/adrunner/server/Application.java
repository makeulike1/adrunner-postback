package com.gnm.adrunner.server;

import com.gnm.adrunner.config.RedisConfig;
import com.gnm.adrunner.util.timeBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {
 
	public static void main(String[] args) {
		
		System.out.println("현재 시각 : "+timeBuilder.getCurrentTime());
		System.out.println("어제 시작 시각 : "+timeBuilder.getYesterdayStartTime());
		System.out.println("어제 종료 시각 : "+timeBuilder.getYesterdayEndTime());
		System.out.println("어제 날짜 : "+timeBuilder.getYesterdayDate());
		System.out.println("오늘 날짜 : "+timeBuilder.getTodayDate());
		System.out.println("오늘 시작 시각 : "+timeBuilder.getTodayStartTime());
		System.out.println("오늘 종료 시각 : "+timeBuilder.getTodayEndTime());


		//REDIS 시작
		RedisConfig.init();
		
		SpringApplication.run(Application.class, args);
	}

}
