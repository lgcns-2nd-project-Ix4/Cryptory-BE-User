package com.cryptory.be;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.SQLOutput;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class UserServer {

//	@Autowired
//	private Environment environment;

	public static void main(String[] args) {
		// 운영 환경이 아니면 .env 파일 로드
		System.out.println(System.getenv().toString());
		String activeProfile = System.getenv("ENV_ACTIVE");
		if (activeProfile == null || !activeProfile.equals("NO")) {
			Dotenv dotenv = Dotenv.load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		}
		SpringApplication.run(UserServer.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception{
//		System.out.println("-------");
//
//		for(String propertyName : environment.getActiveProfiles()){
//			String propertyValue = environment.getProperty(propertyName);
//			System.out.println(propertyName + "=" + propertyValue);
//		}
//	}

}
