package com.wgx.dormitorymanager2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DormitoryManager2Application {

	public static void main(String[] args) {
		SpringApplication.run(DormitoryManager2Application.class, args);
	}

}
