package com.example.myFirstProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//this is entry point for application , this does 3 tasks
// @configuration ,
// @EnableAutoConfiguration and
// @ComponentScan -- this scans all the beans
// package com.example.myFirstProject; inside this package only we should be define beans so that springboot can scan beans

@SpringBootApplication
public class MyFirstProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstProjectApplication.class, args);
	}

}
