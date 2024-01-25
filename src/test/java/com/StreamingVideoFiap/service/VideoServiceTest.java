package com.StreamingVideoFiap.service;

import com.StreamingVideoFiap.model.Video;
import com.StreamingVideoFiap.repositorio.VideoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Arrays;
import java.util.List;

public class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepositorio videoRepositorio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Video video = new Video("video 90", "descricao 90",
                                  "url 90", "01/01/1900", "comedia");

        Mockito.when(videoService.save(Mockito.any(Video.class))).thenReturn(Mono.just(video));

        StepVerifier.create(videoService.save(video))
                .expectNext(video)
                .verifyComplete();
    }

    @Test
    public void testFindAll() {
        // Mock de dados

        Video video1 = new Video("video 91", "descricao 91",
                                "url 90", "01/01/1901", "Comedia");
        Video video2= new Video("video 92", "descricao 92",
                                    "url 90", "01/01/1902", "Comedia Romantica");

        List<Video> videos = Arrays.asList(video1, video2);

        // Mock do repositório
        Mockito.when(videoRepositorio.findAll()).thenReturn(Flux.fromIterable(videos));

        // Chama o método findAll
        Flux<Video> result = videoService.findAll();

        assertEquals(videos, result.collectList().block());
    }

}
