package com.StreamingVideoFiap.repositorio;

import com.StreamingVideoFiap.model.Video;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoRepositorio extends ReactiveMongoRepository<Video, String> {

    Flux<Video> findAll();

    Flux<Video> findByCategoria(String categoria);

    Flux<Video> findByTitulo(String titulo);

    Mono<Long> countAllByTitulo();

    Mono<Long> countDistinctBy();
}
