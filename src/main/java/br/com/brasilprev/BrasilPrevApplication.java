package br.com.brasilprev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.brasilprev")
public class BrasilPrevApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrasilPrevApplication.class, args);
	}

}
