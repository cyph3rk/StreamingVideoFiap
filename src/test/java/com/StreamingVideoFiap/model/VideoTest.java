package com.StreamingVideoFiap.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class VideoTest {

    @Test
    public void criarVideo() {
        // Arrange
        String titulo    = "video 90";
        String descricao = "descricao 90";
        String url       = "url 90";
        String data      = "01/01/1900";

        // Act
        Video video = new Video(titulo, descricao, url, data);

        // Assert
        assertEquals(titulo, video.getTitulo());
        assertEquals(descricao, video.getDescricao());
        assertEquals(url, video.getUrl());
        assertEquals(data, video.getDataPublicacao());

    }
}
