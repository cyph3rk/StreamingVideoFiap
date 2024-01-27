package com.StreamingVideoFiap.service;

import com.StreamingVideoFiap.model.Estatisticas;
import com.StreamingVideoFiap.model.Video;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IVideoService {

    Flux<Video> findAll();

    Mono<Video> save(Video video);

    Mono<Void> remove(String id);

    Mono<Video> buscarPorId(String id);

    <T> Mono<T> monoResponseStatusNotFoundException();

    Mono<Video> atualiza(Video video_new);

    Flux<Video> buscarPorCategoria(String categoria);

    Flux<Video> buscarPorTitulo(String titulo);

    Mono<Estatisticas> relatorio();
}
