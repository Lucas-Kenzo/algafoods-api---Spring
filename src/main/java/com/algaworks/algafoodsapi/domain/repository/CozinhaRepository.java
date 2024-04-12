package com.algaworks.algafoodsapi.domain.repository;

import com.algaworks.algafoodsapi.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
    List<Cozinha> findByNomeContaining(String nome);
}
