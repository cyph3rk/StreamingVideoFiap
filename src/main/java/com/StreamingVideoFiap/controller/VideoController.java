package com.StreamingVideoFiap.controller;

import com.StreamingVideoFiap.controller.form.VideoForm;
import com.StreamingVideoFiap.model.Estatisticas;
import com.StreamingVideoFiap.model.Video;
import com.StreamingVideoFiap.service.IVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;


@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

//    @Autowired
    private final IVideoService videoService;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


//    private final Validator validator;
//
//    private <T> Map<Path, String> validar(T form) {
//        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
//
//        return violacoes.stream().collect(Collectors.toMap(
//                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
//    }

    @GetMapping
    public Flux<Video> getAll() {
        return videoService.findAll();
    }

    @GetMapping("/paginado")
    public Flux<Video> getAllPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataPublicacao") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Query query = new Query().with(pageable);

        return reactiveMongoTemplate.find(query, Video.class);
    }

    @GetMapping("/titulo/{titulo}")
    public Flux<Video> buscarPorTitulo(@PathVariable @Valid String titulo) {
        return videoService.buscarPorTitulo(titulo);
    }

    @GetMapping("/relatorio")
    public Mono<Estatisticas> relatorio() {
        return videoService.relatorio();
    }


    @GetMapping("/{id}")
    public Mono<Video> buscarPorId(@PathVariable String id) {
        return videoService.buscarPorId(id);
    }

    @GetMapping("/categoria/{categoria}")
    public Flux<Video> buscarPorCategoria(@PathVariable @Valid String categoria) {
        return videoService.buscarPorCategoria(categoria);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Video> save(@RequestBody @Valid VideoForm videoForm) {
        Video video = videoForm.toVideo();
        return videoService.save(video);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteVideoPorId(@PathVariable String id) {
        return videoService.remove(id);
    }

    @PutMapping("/{id}")
    public Mono<Video> atualiza(@PathVariable String id, @RequestBody VideoForm videoForm_new) {
        var video_new = videoForm_new.toVideo();
        video_new.setId(id);
        return videoService.atualiza(video_new);
    }

}
