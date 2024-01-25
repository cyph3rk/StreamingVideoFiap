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
                .flatMap(existingVideo -> {
                    existingVideo.setId(video_new.getId());
                    existingVideo.setTitulo(video_new.getTitulo());
                    existingVideo.setDescricao(video_new.getDescricao());
                    existingVideo.setUrl(video_new.getUrl());
                    existingVideo.setDataPublicacao(video_new.getDataPublicacao());
                    existingVideo.setCategoria(video_new.getCategoria());
                    return videoRepositorio.save(existingVideo);
                });
    }

    public Flux<Video> buscarPorCategoria(String categoria) {
        return videoRepositorio.findByCategoria(categoria)
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .log();
    }

    public Flux<Video> buscarPorTitulo(String titulo) {
        return videoRepositorio.findByTitulo(titulo)
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .log();
    }
}
