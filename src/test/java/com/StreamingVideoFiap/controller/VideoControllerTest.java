package com.StreamingVideoFiap.controller;

import com.StreamingVideoFiap.controller.form.VideoForm;
import com.StreamingVideoFiap.handler.GlobalExceptionHandler;
import com.StreamingVideoFiap.model.Estatisticas;
import com.StreamingVideoFiap.model.Video;
import com.StreamingVideoFiap.service.IVideoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VideoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IVideoService videoService;

    AutoCloseable openMocks;;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        VideoController videoController = new VideoController(videoService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void devPermitireRegistrarVideo() throws Exception {

        VideoForm videoForm = new VideoForm();
        videoForm.setTitulo("Titulo");;
        videoForm.setDescricao("descricao");
        videoForm.setUrl("url");
        videoForm.setDataPublicacao("02/02/1902");
        videoForm.setCategoria("categoria");

        Video video = videoForm.toVideo();
        Mono<Video> videoMono = Mono.just(video);

        when(videoService.save(any(Video.class))).thenReturn(videoMono);

        mockMvc.perform(post("/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(videoForm))
        ).andExpect(status().isCreated());

        verify(videoService, times(1)).save(any(Video.class));
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return new ObjectMapper().writeValueAsString(object);
    }

}