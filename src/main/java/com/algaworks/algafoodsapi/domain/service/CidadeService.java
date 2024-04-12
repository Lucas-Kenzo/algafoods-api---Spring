package com.algaworks.algafoodsapi.domain.service;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Cidade;
import com.algaworks.algafoodsapi.domain.model.Estado;
import com.algaworks.algafoodsapi.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    CidadeRepository repository;

    public List<Cidade> findAll(){
        return repository.findAll();
    }

    public Cidade findById(Long id){
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com código %d", id));
        }
    }

    public Cidade salvar(Cidade cidade){
        return repository.salvar(cidade);
    }

    public void excluir(Long id){
        try {
            repository.remover(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com código %d", id));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso", id));
        }
    }
}
