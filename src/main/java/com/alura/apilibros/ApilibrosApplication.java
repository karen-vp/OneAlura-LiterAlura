package com.alura.apilibros;

import com.alura.apilibros.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApilibrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApilibrosApplication.class, args);


	}
	@Override
	public void run(String... args) throws Exception{
		Principal principal = new Principal();
		principal.programaPrincipal();
	}

}
