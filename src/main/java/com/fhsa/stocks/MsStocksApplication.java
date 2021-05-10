package com.fhsa.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.fhsa.stocks")
public class MsStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsStocksApplication.class, args);
	}

}
