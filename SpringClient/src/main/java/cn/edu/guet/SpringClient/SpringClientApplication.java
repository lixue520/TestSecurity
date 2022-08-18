package cn.edu.guet.SpringClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = "cn.edu.guet") //扫描同包
public class SpringClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringClientApplication.class, args);
	}

}
