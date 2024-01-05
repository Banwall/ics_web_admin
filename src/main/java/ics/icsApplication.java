package ics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class icsApplication {

	public static void main(String[] args) {
		SpringApplication.run(icsApplication.class, args);
	}

}
