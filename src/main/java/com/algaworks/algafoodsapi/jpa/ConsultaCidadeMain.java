package com.algaworks.algafoodsapi.jpa;

import com.algaworks.algafoodsapi.AlgafoodsApiApplication;
import com.algaworks.algafoodsapi.domain.model.Cidade;
import com.algaworks.algafoodsapi.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCidadeMain {

    public static void main(String[] args){
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CidadeRepository cidades = applicationContext.getBean(CidadeRepository.class);

        List<Cidade> todasCidades = cidades.findAll();

        for(Cidade cidade : todasCidades){
            System.out.println(cidade.getNome());
        }
    }

}
