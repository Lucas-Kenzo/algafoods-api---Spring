package com.algaworks.algafoodsapi.infrastructure.repository;

import com.algaworks.algafoodsapi.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFretefinal);

    List<Restaurante> findComFreteGratis(String nome);
}
