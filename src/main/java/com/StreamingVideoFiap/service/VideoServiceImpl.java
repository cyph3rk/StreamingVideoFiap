package com.StreamingVideoFiap.service;

import com.StreamingVideoFiap.model.Estatisticas;
import com.StreamingVideoFiap.model.Video;
import com.StreamingVideoFiap.repositorio.VideoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements IVideoService {

//    @Autowired
    private final VideoRepositorio videoRepositorio;

    @Override
    public Flux<Video> findAll() {
        return videoRepositorio.findAll();
    }

    @Override
    public Mono<Video> save(Video video) {
        return videoRepositorio.save(video);
    }

    @Override
    public Mono<Void> remove(String id) {
        return buscarPorId(id).flatMap(videoRepositorio::delete);
    }

    @Override
    public Mono<Video> buscarPorId(String id) {
        return videoRepositorio.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .log();
    }

    @Override
    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Video nao localizado"));
    }

    @Override
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

    @Override
    public Flux<Video> buscarPorCategoria(String categoria) {
        return videoRepositorio.findByCategoria(categoria)
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .log();
    }

    @Override
    public Flux<Video> buscarPorTitulo(String titulo) {
        return videoRepositorio.findByTitulo(titulo)
                .switchIfEmpty(monoResponseStatusNotFoundException())
                .log();
    }

    @Override
    public Mono<Estatisticas> relatorio() {
        Long totalVideos = videoRepositorio.countDistinctBy().block();

        Estatisticas estatisticas = new Estatisticas();
        if (totalVideos > 0) {
            estatisticas.setTotalVideos(totalVideos);
        } else {
            estatisticas.setTotalVideos(0L);
        }

        Mono<Estatisticas> estatisticasMono = Mono.just(estatisticas);

        return estatisticasMono;
    }
}
