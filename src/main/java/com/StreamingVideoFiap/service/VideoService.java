package com.StreamingVideoFiap.service;

import com.StreamingVideoFiap.model.Video;
import com.StreamingVideoFiap.repositorio.VideoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepositorio videoRepositorio;

    public Flux<Video> findAll() {
        return videoRepositorio.findAll();
    }

    public Mono<Video> save(Video video) {
        return videoRepositorio.save(video);
    }

//    public Mono<Void> remove(String id) {
//        return videoRepositorio.deleteById(id);
//    }

    public Mono<Void> remove(String id) {
        return buscarPorId(id).flatMap(videoRepositorio::delete);
    }

    public Mono<Video> buscarPorId(String id) {
        return videoRepositorio.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .log();
    }

    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Video nao localizado"));
    }

    public Mono<Video> atualiza(Video video_new) {
        return buscarPorId(video_new.getId())
                .flatMap(existingProduct -> {
                    existingProduct.setId(video_new.getId());
                    existingProduct.setTitulo(video_new.getTitulo());
                    existingProduct.setDescricao(video_new.getDescricao());
                    existingProduct.setUrl(video_new.getUrl());
                    existingProduct.setDataPublicacao(video_new.getDataPublicacao());
                    return videoRepositorio.save(existingProduct);
                });
    }

}
