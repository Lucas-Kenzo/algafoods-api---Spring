package com.algaworks.algafoodsapi.domain.repository;

import com.algaworks.algafoodsapi.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> findAll();
    Estado findById(Long id);
    Estado salvar(Estado estado);
    void remover(Long id);
}

