package com.algaworks.algafoodsapi.domain.repository;

import com.algaworks.algafoodsapi.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> todas();
    Permissao buscar(Long id);
    Permissao adicionar(Permissao permissao);
    void remover(Permissao permissao);
}
