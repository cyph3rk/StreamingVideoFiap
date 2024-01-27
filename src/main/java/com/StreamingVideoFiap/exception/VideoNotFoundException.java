package com.StreamingVideoFiap.exception;

public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(String mensagem) {
        super(mensagem);
    }

}
