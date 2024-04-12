package com.algaworks.algafoodsapi.domain.service;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Cozinha;
import com.algaworks.algafoodsapi.domain.model.Restaurante;
import com.algaworks.algafoodsapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodsapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> findAll(){
        return repository.findAll();
    }

    public Restaurante findById(Long id){
        return repository.findById(id).get();
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).
                orElseThrow(() -> new EntidadeNaoEncontradaException(
                    String.format("Cozinha de id %d não existe", cozinhaId )));
        restaurante.setCozinha(cozinha);

        return repository.save(restaurante);

    }

    public List<Restaurante> buscarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        return repository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    public void excluir(Long id){
        try {
            repository.deleteById(id);

        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de restaurante com código %d", id)
            );

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Restaurante de código %d não pode ser removida, pois está em uso", id));
        }
    }
}
