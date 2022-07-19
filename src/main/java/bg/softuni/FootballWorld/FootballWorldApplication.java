package bg.softuni.FootballWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootballWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballWorldApplication.class, args);
	}

}
