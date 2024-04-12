package com.algaworks.algafoodsapi.infrastructure.repository;

import com.algaworks.algafoodsapi.domain.model.Estado;
import com.algaworks.algafoodsapi.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<Estado> findAll() {
        return manager.createQuery("from Estado", Estado.class)
                .getResultList();
    }

    @Override
    public Estado findById(Long id) {
        var estado = manager.find(Estado.class, id);

        if(estado != null){
            return estado;
        }
        throw new EmptyResultDataAccessException(1);
    }

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        var estado = findById(id);
        manager.remove(estado);
    }
}
