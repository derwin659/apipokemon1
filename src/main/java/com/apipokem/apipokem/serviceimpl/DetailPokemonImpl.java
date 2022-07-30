package com.apipokem.apipokem.serviceimpl;


import com.apipokem.apipokem.model.Lista;
import com.apipokem.apipokem.model.Lista1;
import com.apipokem.apipokem.service.DetailPokemonService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import java.time.Duration;

/**
 * Servicio que consulta la lista de detalles del pokemon
 */
@Service
public class DetailPokemonImpl implements DetailPokemonService {
    private static final Logger logger = LoggerFactory.getLogger(DetailPokemonImpl.class);
    RestTemplate restTemplate = new RestTemplate();
    int connectTimeoutMillis = 30000;
    int readTimeoutMillis = 30000;

    private static final String URL_SERVICE = "https://apipokemon1.herokuapp.com/v1/pokemon/detail";

    public DetailPokemonImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(connectTimeoutMillis))
                .setReadTimeout(Duration.ofMillis(readTimeoutMillis))
                .build();
    }

    public DetailPokemonImpl() {
    }

    @Override
    public Lista getPokemon(Integer id, String protocoloService, String hostPokemon, String contentPokemon) {

        logger.info("****RESP SERVICIO DETALLES DEL POKEMON**********");

        String endpointPokemon = protocoloService + hostPokemon + contentPokemon + "/" + id;
        logger.info(endpointPokemon);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Api de prueba Pokedex");
        HttpEntity<String> entityd = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(endpointPokemon, HttpMethod.GET, entityd, String.class);
        try {



            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Lista lista = new Lista();
                JSONObject jsonObject = new JSONObject((String) responseEntity.getBody());
                JSONArray abilities = jsonObject.getJSONArray("abilities");
                JSONArray types = jsonObject.getJSONArray("types");
                String imagen = String.valueOf(jsonObject.getJSONObject("sprites").get("back_default"));
                int weight = (int) jsonObject.get("weight");
                lista.setWeight(weight);
                lista.setAbilities(abilities.toList());
                lista.setTypes(types.toList());
                lista.setImage(imagen);
                lista.setUrl(URL_SERVICE +"/"+ id);
                return lista;
            }
        } catch (HttpClientErrorException e) {
            logger.info("****error cliente*********");
        }catch (ServerErrorException e){
            logger.info("***A ocurrido un error en el servicio de pokemon*********" + e.getMessage());
        }
        catch (Exception e) {
            logger.info("***A ocurrido un error*********" + e.getMessage());
        }

        return null;
    }

    @Override
    public Lista1 getPokemonDescriptions(Integer id, String protocoloService, String hostPokemon, String contentPokemon) {
        logger.info("****RESP SERVICIO DETALLES DEL POKEMON**********");

        String endpointPokemonDescripcion = protocoloService + hostPokemon + "/api/v2/characteristic/" + id;

        System.out.println(endpointPokemonDescripcion);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Api de prueba Pokedex");
        HttpEntity<String> entityd = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(endpointPokemonDescripcion, HttpMethod.GET, entityd, String.class);
        try {

            logger.info("***PASO OK 1*********");

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                JSONObject jsonObject = new JSONObject((String) responseEntity.getBody());
                JSONArray descriptions = jsonObject.getJSONArray("descriptions");
                Lista1 lista1=new Lista1();

                Lista lista=getLista(id,protocoloService,hostPokemon,contentPokemon);

                lista1.setAbilities(lista.getAbilities());
                lista1.setImage(lista.getImage());
                lista1.setUrl(lista.getImage());
                lista1.setWeight(lista.getWeight());
                lista1.setTypes(lista.getTypes());
                lista1.setDescriptions(descriptions.toList());


                lista1.setEvolutions(getListaEvolutions(id, protocoloService, hostPokemon, contentPokemon).toList()); ;
                lista1.setUrl(URL_SERVICE +"/"+ id);
                return lista1;
            }
        } catch (HttpClientErrorException e) {
            logger.info("****error cliente*********");
        }catch (ServerErrorException e){
            logger.info("***A ocurrido un error en el servicio de pokemon*********" + e.getMessage());
        }
        catch (Exception e) {
            logger.info("***A ocurrido un error*********" + e.getMessage());
        }

        return null;
    }

    private JSONArray getListaEvolutions(Integer id, String protocoloService, String hostPokemon, String contentPokemon) {

        String endpointPokemonEvolutions = protocoloService + hostPokemon + "/api/v2/evolution-chain/" + id;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Api de prueba Pokedex");
        HttpEntity<String> entityd = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(endpointPokemonEvolutions, HttpMethod.GET, entityd, String.class);
        try {

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                JSONObject jsonObject = new JSONObject((String) responseEntity.getBody());
                JSONObject evolutionList = jsonObject.getJSONObject("chain");
                return evolutionList.getJSONArray("evolves_to");
            }
        } catch (HttpClientErrorException e) {
            logger.info("****error cliente*********");
        }catch (ServerErrorException e){
            logger.info("***A ocurrido un error en el servicio de pokemon*********" + e.getMessage());
        }
        catch (Exception e) {
            logger.info("***A ocurrido un error*********" + e.getMessage());
        }

        return null;

    }


    private Lista getLista(Integer id, String protocoloService, String hostPokemon, String contentPokemon){
        String endpointPokemon = protocoloService + hostPokemon + contentPokemon + "/" + id;
        logger.info(endpointPokemon);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Api de prueba Pokedex");
        HttpEntity<String> entityd = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(endpointPokemon, HttpMethod.GET, entityd, String.class);
        try {

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Lista lista = new Lista();
                JSONObject jsonObject = new JSONObject((String) responseEntity.getBody());
                JSONArray abilities = jsonObject.getJSONArray("abilities");
                JSONArray types = jsonObject.getJSONArray("types");
                String imagen = String.valueOf(jsonObject.getJSONObject("sprites").get("back_default"));
                String descriptions = String.valueOf(jsonObject.getJSONObject("sprites").get("back_default"));
                String evolutions = String.valueOf(jsonObject.getJSONObject("sprites").get("back_default"));
                int weight = (int) jsonObject.get("weight");
                lista.setWeight(weight);
                lista.setAbilities(abilities.toList());
                lista.setTypes(types.toList());
                lista.setImage(imagen);
                lista.setUrl(URL_SERVICE +"/"+ id);
                return lista;
            }
        } catch (HttpClientErrorException e) {
            logger.info("****error cliente*********");
        }catch (ServerErrorException e){
            logger.info("***A ocurrido un error en el servicio de pokemon*********" + e.getMessage());
        }
        catch (Exception e) {
            logger.info("***A ocurrido un error*********" + e.getMessage());
        }

        return null;
    }
}
