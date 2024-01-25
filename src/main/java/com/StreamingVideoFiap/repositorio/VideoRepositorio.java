package com.StreamingVideoFiap.repositorio;

import com.StreamingVideoFiap.model.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoRepositorio extends ReactiveMongoRepository<Video, String> {

//    Mono<Video> findByNome(String nome);

    Flux<Video> findAll();


}
