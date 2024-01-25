package com.StreamingVideoFiap.controller.form;

import com.StreamingVideoFiap.model.Video;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoForm {

    @JsonProperty
    @NotNull
    @NotBlank(message = "Campo TITULO é obrigatorio")
    private String titulo;

    @JsonProperty
    @NotBlank(message = "Campo DESCRICAO é obrigatorio")
    private String descricao;

    @JsonProperty
    @NotBlank(message = "Campo URL é obrigatorio")
    private String url;

    @JsonProperty
    private String dataPublicacao;

    public Video toVideo() {
        return new Video(titulo, descricao, url, dataPublicacao);
    }


}
