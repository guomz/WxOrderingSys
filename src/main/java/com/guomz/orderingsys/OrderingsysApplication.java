package com.guomz.orderingsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = "com.guomz.orderingsys.dao")
@EnableScheduling
@EnableCaching
public class OrderingsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderingsysApplication.class, args);
	}

}
