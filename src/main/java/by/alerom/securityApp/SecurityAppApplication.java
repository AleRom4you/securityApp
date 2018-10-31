package by.alerom.securityApp;

import by.alerom.securityApp.rsa.GenerateKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityAppApplication {

	public static void main(String[] args) {
//		GenerateKeys.generate();

		SpringApplication.run(SecurityAppApplication.class, args);
	}
}
