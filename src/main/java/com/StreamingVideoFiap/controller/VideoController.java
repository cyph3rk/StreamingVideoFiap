package com.StreamingVideoFiap.controller;

import com.StreamingVideoFiap.controller.form.VideoForm;
import com.StreamingVideoFiap.model.Video;
import com.StreamingVideoFiap.service.VideoService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private final Validator validator;

    public VideoController(Validator validator) {
        this.validator = validator;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

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
    public Flux<Video> buscarPorTitulo(@PathVariable String titulo) {
        return videoService.buscarPorTitulo(titulo);
    }

    @GetMapping("/{id}")
    public Mono<Video> buscarPorId(@PathVariable String id) {
        return videoService.buscarPorId(id);
    }

    @GetMapping("/categoria/{categoria}")
    public Flux<Video> buscarPorCategoria(@PathVariable String categoria) {
        return videoService.buscarPorCategoria(categoria);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Video> save(@Valid @RequestBody VideoForm videoForm) {
        Video video = videoForm.toVideo();
        return videoService.save(video);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteVideoPorId(@PathVariable String id) {
        return videoService.remove(id);
    }

    @PutMapping("/{id}")
    public Mono<Video> atualiza(@PathVariable String id, @Valid @RequestBody VideoForm videoForm_new) {
        var video_new = videoForm_new.toVideo();
        video_new.setId(id);
        return videoService.atualiza(video_new);
    }

}
