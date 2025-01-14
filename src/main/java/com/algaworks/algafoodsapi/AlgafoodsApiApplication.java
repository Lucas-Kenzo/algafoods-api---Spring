package com.algaworks.algafoodsapi;

import com.algaworks.algafoodsapi.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodsApiApplication.class, args);
	}

}
