package com.algaworks.algafoodsapi.domain.repository;

import com.algaworks.algafoodsapi.domain.model.Cidade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository {

    List<Cidade> findAll();
    Cidade findById(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Long id);
}
