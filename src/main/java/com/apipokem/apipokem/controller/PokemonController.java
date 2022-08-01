package com.apipokem.apipokem.controller;


import com.apipokem.apipokem.model.Lista;
import com.apipokem.apipokem.model.Lista1;
import com.apipokem.apipokem.model.Results;
import com.apipokem.apipokem.serviceimpl.AllPokemonImpl;
import com.apipokem.apipokem.serviceimpl.DetailPokemonImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@EnableCaching
@RequestMapping({"/v1"})
@CrossOrigin("*")
public class PokemonController {
    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Value("${protocolo.services}")
    String protocoloService;

    @Value("${connect.host.pokemon}")
    String hostPokemon;

    @Value("${connect.content.pokemon}")
    String contentPokemon;

    @Autowired
    private DetailPokemonImpl detailPokemon;

    @Autowired
    private AllPokemonImpl allPokemon;

    public PokemonController(DetailPokemonImpl detailPokemon) {
        this.detailPokemon = detailPokemon;
    }

    @GetMapping("/pokemones")
    @ResponseBody
    public ResponseEntity<?> pokemon(@RequestParam(defaultValue = "20") int offset,@RequestParam(defaultValue = "20") int limit) throws Exception, NoSuchElementException, NullPointerException {

        System.out.println(offset);
        System.out.println(limit);
        List<Results> listado= this.allPokemon.getAllPokemon(protocoloService,hostPokemon,contentPokemon,offset,limit);
        System.out.println("listado.size()");
        System.out.println(listado.size());
        int cantidad= listado.size();

        List<Lista> lista= new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            String valor =listado.get(i).getUrl().toString().substring(34,36);
            if(valor.substring(1,2).equals("/") ){
                Integer numPokemon=Integer.valueOf(listado.get(i).getUrl().toString().substring(34,35));
                lista.add(i,detailPokemon1(numPokemon));
            }else{
                Integer numPokemon=Integer.valueOf(listado.get(i).getUrl().toString().substring(34,36));
                lista.add(i,detailPokemon1(numPokemon));
            }

        }
        return new ResponseEntity<>(lista, HttpStatus.OK);

    }
    @GetMapping("/pokemon/detail/{id}")
    @ResponseBody
    public ResponseEntity<Lista1> detailPokemon(@PathVariable Integer id) throws Exception, NoSuchElementException, NullPointerException {
        logger.info("****DETAIL POKEMON**********");
        System.out.println(id);
        Lista1 listaDetail= this.detailPokemon.getPokemonDescriptions(id,protocoloService,hostPokemon,contentPokemon);

        System.out.println(listaDetail);
        return new ResponseEntity<Lista1>(listaDetail, HttpStatus.OK);

    }

    private Lista detailPokemon1(Integer id) throws Exception, NoSuchElementException, NullPointerException {
        logger.info("****DETAIL POKEMON 1**********");
        Lista listaDetail= this.detailPokemon.getPokemon(id,protocoloService,hostPokemon,contentPokemon);
        System.out.println(listaDetail.getAbilities());
        return listaDetail;

    }




}



