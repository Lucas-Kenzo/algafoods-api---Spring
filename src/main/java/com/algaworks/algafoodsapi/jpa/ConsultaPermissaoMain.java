package com.algaworks.algafoodsapi.jpa;

import com.algaworks.algafoodsapi.AlgafoodsApiApplication;
import com.algaworks.algafoodsapi.domain.model.Permissao;
import com.algaworks.algafoodsapi.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaPermissaoMain {

    public static void main(String[] args){
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        PermissaoRepository permissaos = applicationContext.getBean(PermissaoRepository.class);

        List<Permissao> todasPermissaos = permissaos.todas();

        for(Permissao permissao : todasPermissaos){
            System.out.println(permissao.getDescricao());
        }
    }

}
