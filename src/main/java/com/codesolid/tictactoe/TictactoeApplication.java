package com.codesolid.tictactoe;

import com.codesolid.tictactoe.model.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
	}

	@Bean
	public Game game() {
		return new Game();
	}
}
