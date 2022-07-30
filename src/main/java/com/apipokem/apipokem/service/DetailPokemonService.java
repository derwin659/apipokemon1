package com.apipokem.apipokem.service;


import com.apipokem.apipokem.model.Lista;
import com.apipokem.apipokem.model.Lista1;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface DetailPokemonService {

  Lista getPokemon(Integer id, String protocoloService, String hostPokemon, String contentPokemon);

  Lista1 getPokemonDescriptions(Integer id, String protocoloService, String hostPokemon, String contentPokemon);




}
