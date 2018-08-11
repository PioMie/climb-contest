package cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClimbContestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClimbContestApplication.class, args);
	}
	
	@GetMapping("/")
    public String hello() {
            return "Hello Spring Boot on Google Cloud Engine..!";
    }
}
