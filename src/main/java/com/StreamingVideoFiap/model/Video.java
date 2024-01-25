package com.StreamingVideoFiap.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection = "video")
public class Video {

    @Id
    private String id;

    private String titulo;

    private String descricao;

    private String url;

    private String dataPublicacao;

    public Video(String titulo, String descricao, String url, String dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.dataPublicacao = dataPublicacao;
    }

}
