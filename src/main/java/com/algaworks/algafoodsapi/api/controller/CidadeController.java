package com.algaworks.algafoodsapi.api.controller;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Cidade;
import com.algaworks.algafoodsapi.domain.model.Restaurante;
import com.algaworks.algafoodsapi.domain.service.CidadeService;
import com.algaworks.algafoodsapi.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService service;
    private EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<Cidade>> findAll(){
        var cidades = service.findAll();
        return ResponseEntity.ok(cidades);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cidade> findById(@PathVariable Long id){
        try {
            var cidade = service.findById(id);
            return ResponseEntity.ok(cidade);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody Cidade cidade){
        return service.salvar(cidade);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cidade> editar(@PathVariable Long id, @RequestBody Cidade cidade){
        try {
            var cidadeAtual = service.findById(id);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            service.salvar(cidadeAtual);
            return ResponseEntity.ok(cidadeAtual);

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
