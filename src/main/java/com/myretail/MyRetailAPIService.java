package com.myretail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
		"com.myretail",
		"com.myretail.api.home" ,
		"com.myretail.api.product",
		"com.myretail.config"
})
public class MyRetailAPIService {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailAPIService.class, args);
	}

}
