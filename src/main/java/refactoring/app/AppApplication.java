package refactoring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import refactoring.app.domain.Invoice;
import refactoring.app.domain.Performance;
import refactoring.app.domain.Play;
import refactoring.app.domain.Plays;

@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
