package com.estuate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@Component

public class App {

	public static void main(String[] args) {
		System.out.println("Hello world");
		SpringApplication.run(App.class, args);
	}
}
