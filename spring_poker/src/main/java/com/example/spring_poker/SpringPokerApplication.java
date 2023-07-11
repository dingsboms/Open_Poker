package com.example.spring_poker;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.example.spring_poker.api.Controller;
import com.example.spring_poker.api.Model.Player;
import com.example.spring_poker.api.Model.Table;
import com.example.spring_poker.api.View.Setup_View;
import com.example.spring_poker.api.View.Table_View;

@SpringBootApplication
public class SpringPokerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringPokerApplication.class).headless(false).run(args);
	}

}
