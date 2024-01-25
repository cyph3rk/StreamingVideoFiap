package com.StreamingVideoFiap.Integracao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VideoTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testeCadastrandoVideoSucesso() {

        String url = "http://localhost:" + port + "/videos";

        String requestBody = "{\"titulo\":\"Filme Teste 001\"," +
                "\"descricao\":\"Cadastro do filme 001 para teste\"," +
                "\"url\":\"www.teste001.com\"," +
                "\"dataPublicacao\":\"01/01/1900\"," +
                "\"categoria\":\"terror\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testeDeletandoVideoSucesso() {

        String url = "http://localhost:" + port + "/videos";

        String requestBody = "{\"titulo\":\"Filme Teste 002 Delete\"," +
                "\"descricao\":\"Cadastro do filme 002 para teste delete\"," +
                "\"url\":\"www.teste002.com\"," +
                "\"dataPublicacao\":\"02/02/1902\"," +
                "\"categoria\":\"terror comedia\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();

            url = "http://localhost:" + port + "/videos/" + id;

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeBuscaVideoPorIdSucesso() {

        String url = "http://localhost:" + port + "/videos";

        String requestBody = "{\"titulo\":\"Filme Teste 003\"," +
                "\"descricao\":\"Cadastro do filme 003\"," +
                "\"url\":\"www.teste003.com\"," +
                "\"dataPublicacao\":\"03/03/1903\"," +
                "\"categoria\":\"comedia\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();

            url = "http://localhost:" + port + "/videos/" + id;

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

            String resp = "{\"id\":\"" + id + "\"," +
                    "\"titulo\":\"Filme Teste 003\"," +
                    "\"descricao\":\"Cadastro do filme 003\"," +
                    "\"url\":\"www.teste003.com\"," +
                    "\"dataPublicacao\":\"03/03/1903\"," +
                    "\"categoria\":\"comedia\"}";

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeBuscaVideoPorCategoriaSucesso() {

        String url = "http://localhost:" + port + "/videos";

        String requestBody = "{\"titulo\":\"Filme Teste 004\"," +
                "\"descricao\":\"Cadastro do filme 004\"," +
                "\"url\":\"www.teste004.com\"," +
                "\"dataPublicacao\":\"04/04/1904\"," +
                "\"categoria\":\"teste categoria 04\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();
            String categoria = jsonNode.get("categoria").asText();

            url = "http://localhost:" + port + "/videos/categoria/" + categoria;

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

            String resp = "[{\"id\":\"" + id + "\"," +
                    "\"titulo\":\"Filme Teste 004\"," +
                    "\"descricao\":\"Cadastro do filme 004\"," +
                    "\"url\":\"www.teste004.com\"," +
                    "\"dataPublicacao\":\"04/04/1904\"," +
                    "\"categoria\":\"teste categoria 04\"}]";

            System.out.println("response: " + response.getBody() );
            System.out.println("resp: "+ resp);

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeAtualizaVideoSucesso() {

        String url = "http://localhost:" + port + "/videos";

        String requestBody = "{\"titulo\":\"Filme Teste 005\"," +
                "\"descricao\":\"Cadastro do filme 005\"," +
                "\"url\":\"www.teste005.com\"," +
                "\"dataPublicacao\":\"05/05/1905\"," +
                "\"categoria\":\"terror comedia\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();

            url = "http://localhost:" + port + "/videos/" + id;

            requestBody = "{\"titulo\":\"Filme Teste 006\"," +
                    "\"descricao\":\"Cadastro do filme 006\"," +
                    "\"url\":\"www.teste006.com\"," +
                    "\"dataPublicacao\":\"06/06/1906\"," +
                    "\"categoria\":\"romance\"}";

            requestEntity = new HttpEntity<>(requestBody, headers);
            response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            String resp = "{\"id\":\"" + id + "\"," +
                    "\"titulo\":\"Filme Teste 006\"," +
                    "\"descricao\":\"Cadastro do filme 006\"," +
                    "\"url\":\"www.teste006.com\"," +
                    "\"dataPublicacao\":\"06/06/1906\"," +
                    "\"categoria\":\"romance\"}";

            System.out.println("response: " + response.getBody() );
            System.out.println("resp: "+ resp);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeBuscaVideoPorTituloSucesso() {

        String url = "http://localhost:" + port + "/videos";

        String requestBody = "{\"titulo\":\"Filme Teste 010 A\"," +
                "\"descricao\":\"Cadastro do filme 010\"," +
                "\"url\":\"www.teste010.com\"," +
                "\"dataPublicacao\":\"10/10/1910\"," +
                "\"categoria\":\"melodico\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            String id = jsonNode.get("id").asText();
            String titulo = jsonNode.get("titulo").asText();

            url = "http://localhost:" + port + "/videos/titulo/" + titulo;

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

            String resp = "[{\"id\":\"" + id + "\"," +
                    "\"titulo\":\"Filme Teste 010 A\"," +
                    "\"descricao\":\"Cadastro do filme 010\"," +
                    "\"url\":\"www.teste010.com\"," +
                    "\"dataPublicacao\":\"10/10/1910\"," +
                    "\"categoria\":\"melodico\"}]";

            System.out.println("response: " + response.getBody() );
            System.out.println("resp: "+ resp);

            Assert.assertTrue(response.getBody() != null && response.getBody().contains(resp));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
