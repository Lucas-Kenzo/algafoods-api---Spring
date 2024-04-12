package com.algaworks.algafoodsapi.jpa;

import com.algaworks.algafoodsapi.AlgafoodsApiApplication;
import com.algaworks.algafoodsapi.domain.model.Cozinha;
import com.algaworks.algafoodsapi.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args){
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha> todasCozinhas = cozinhas.findAll();

        for(Cozinha cozinha : todasCozinhas){
            System.out.println(cozinha.getNome());
        }

    }

}
