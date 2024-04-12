package com.algaworks.algafoodsapi.domain.service;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Cozinha;
import com.algaworks.algafoodsapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {

    @Autowired
    CozinhaRepository repository;


    public List<Cozinha> findAll(){
        return repository.findAll();
    }

    public Optional<Cozinha> findById(Long id){
        return repository.findById(id);
    }

    public Cozinha salvar(Cozinha cozinha){
        return repository.save(cozinha);
    }

    public void excluir(Long id){
        try {
            repository.deleteById(id);

        }catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cozinha com código %d", id));

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
        }
    }


}
