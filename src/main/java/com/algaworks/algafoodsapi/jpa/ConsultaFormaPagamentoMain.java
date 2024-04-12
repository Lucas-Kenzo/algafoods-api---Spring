package com.algaworks.algafoodsapi.jpa;

import com.algaworks.algafoodsapi.AlgafoodsApiApplication;
import com.algaworks.algafoodsapi.domain.model.FormaPagamento;
import com.algaworks.algafoodsapi.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaFormaPagamentoMain {

    public static void main(String[] args){
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentos = applicationContext.getBean(FormaPagamentoRepository.class);

        List<FormaPagamento> todasFormaPagamentos = formaPagamentos.todas();

        for(FormaPagamento formaPagamento : todasFormaPagamentos){
            System.out.println(formaPagamento.getDescricao());
        }
    }

}
