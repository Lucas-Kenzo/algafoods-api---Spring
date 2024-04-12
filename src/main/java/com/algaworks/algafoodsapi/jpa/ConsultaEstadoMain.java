package com.algaworks.algafoodsapi.jpa;

import com.algaworks.algafoodsapi.AlgafoodsApiApplication;
import com.algaworks.algafoodsapi.domain.model.Estado;
import com.algaworks.algafoodsapi.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaEstadoMain {

    public static void main(String[] args){
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);

        List<Estado> todasEstados = estados.findAll();

        for(Estado estado : todasEstados){
            System.out.println(estado.getNome());
        }
    }

}
