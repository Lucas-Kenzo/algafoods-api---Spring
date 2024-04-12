package com.algaworks.algafoodsapi.domain.service;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Estado;
import com.algaworks.algafoodsapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<Estado> findAll(){
        return repository.findAll();
    }

    public Estado findById(Long id){
        try {
            return repository.findById(id);

        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Estado de id %d não encontrado", id)
            );
        }
    }

    public Estado salvar(Estado estado){
        return repository.salvar(estado);
    }

    public void  excluir(Long id){
        try {
            repository.remover(id);

        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Estado de id %d não encontrado", id)
            );

        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Não é possível excluir o Estado de id %d, pois ele ainda está sendo usado", id)
            );
        }
    }
}
