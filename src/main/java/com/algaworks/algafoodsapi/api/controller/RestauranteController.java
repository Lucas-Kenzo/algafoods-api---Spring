package com.algaworks.algafoodsapi.api.controller;

import com.algaworks.algafoodsapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodsapi.domain.model.Restaurante;
import com.algaworks.algafoodsapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodsapi.domain.service.RestauranteService;
import com.algaworks.algafoodsapi.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import com.algaworks.algafoodsapi.infrastructure.repository.spec.RestauranteComNomeSemelhanteSpec;
import static com.algaworks.algafoodsapi.infrastructure.repository.spec.RestauranteSpecs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteService service;
    @Autowired
    RestauranteRepository repository;

    @GetMapping
    public ResponseEntity<List<Restaurante>> findAll(){
        var restaurantes = service.findAll();
        return ResponseEntity.ok(restaurantes);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Restaurante> findById(@PathVariable Long id){
        var restaurante = service.findById(id);

        if(restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/por-nome")
    public ResponseEntity<List<Restaurante>> buscarPorNome(String nome, BigDecimal taxaFreteInicial,
                                                           BigDecimal taxaFreteFinal){
        var restaurantes = service.buscarPorNome(nome, taxaFreteInicial, taxaFreteFinal);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping(value = "/com-frete-gratis")
    public List<Restaurante> restaurantesComfreteGratis(String nome){
        return repository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

    @GetMapping(value = "/primeiro")
    public Optional<Restaurante> restaurantePrimeiro(){
        return repository.buscarPrimeiro();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante){
        try {
            restaurante =  service.salvar(restaurante);
            return ResponseEntity.ok(restaurante);

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editar(@RequestBody Restaurante restaurante, @PathVariable  Long id) {
        try {
            var restauranteAtual = service.findById(id);

            if (restauranteAtual != null) {
                BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamento", "endereco", "data_cadastro", "produtos");
                service.salvar(restauranteAtual);
                return ResponseEntity.ok(restauranteAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Restaurante> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        var restauranteAtual = service.findById(id);

        if(restauranteAtual == null){
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual);
        editar(restauranteAtual, id);
        return ResponseEntity.ok().build();
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);


        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            assert field != null;
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

}
