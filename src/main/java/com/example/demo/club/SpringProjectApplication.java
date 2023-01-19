package com.example.demo.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringProjectApplication {

	public static void main(String[] args) {
		System.out.println("args:" + args);
		/*ApplicationContext applicationContext =
				SpringApplication.run(SpringProjectApplication.class, args);

		for (String name : applicationContext.getBeanDefinitionNames()) {
			System.out.println("springName:" + name);
		}*/
		SpringApplication.run(SpringProjectApplication.class, args);
	}

}
