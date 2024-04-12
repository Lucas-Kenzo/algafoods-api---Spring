package com.algaworks.algafoodsapi.api.controller;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Estado;
import com.algaworks.algafoodsapi.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    EstadoService service;

    @GetMapping
    public ResponseEntity<List<Estado>> findAll(){
        var estados = service.findAll();
        return ResponseEntity.ok(estados);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Estado> findById(@PathVariable Long id){
        try {
            var cidade = service.findById(id);
            return ResponseEntity.ok(cidade);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody Estado estado){

        return service.salvar(estado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Estado> editar(@PathVariable Long id, @RequestBody Estado estado){
        try {
            var estadoAtual = service.findById(id);
            BeanUtils.copyProperties(estado, estadoAtual, "id");
            service.salvar(estadoAtual);
            return ResponseEntity.ok(estadoAtual);

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id){
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
