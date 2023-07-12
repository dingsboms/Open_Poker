package com.example.spring_poker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SpringPokerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringPokerApplication.class).headless(false).run(args);
	}

}
