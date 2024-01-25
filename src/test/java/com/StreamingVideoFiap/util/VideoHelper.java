package com.StreamingVideoFiap.util;

import com.StreamingVideoFiap.model.Video;

import java.util.UUID;

public abstract class VideoHelper {

    public static Video gerarVideo() {

        return new Video("video 91", "descricao 91",
                            "url 90", "01/01/1901", "comedia");

    }

}
