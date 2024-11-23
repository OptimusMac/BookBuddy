package ru.optimius.bookbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookBuddyApplication.class, args);
	}

}
