package com.algaworks.algafoodsapi.api.controller;

import com.algaworks.algafoodsapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Cozinha;
import com.algaworks.algafoodsapi.domain.model.Restaurante;
import com.algaworks.algafoodsapi.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/cozinhas" )
public class CozinhaController {

    @Autowired
    private CozinhaService service;

    @GetMapping
    public List<Cozinha> listar(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id){
        var cozinha = service.findById(id);

        if(cozinha.isPresent()){
            return ResponseEntity.ok().body(cozinha.get());
        }

        return ResponseEntity.notFound().build();

//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .headers(headers)
//                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionar(@RequestBody Cozinha cozinha){
        service.salvar(cozinha);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cozinha> editar(@PathVariable Long id, @RequestBody Cozinha cozinha){
        var cozinhaAtual = service.findById(id);

        if(cozinhaAtual.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
            Cozinha cozinhaSalva = service.salvar(cozinhaAtual.get());
            return ResponseEntity.ok(cozinhaSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cozinha> excluir(@PathVariable Long id){
        try{
            service.excluir(id);
            return ResponseEntity.noContent().build();

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.noContent().build();


        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}
