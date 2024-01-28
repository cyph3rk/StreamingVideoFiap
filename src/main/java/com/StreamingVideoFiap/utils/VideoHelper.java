package com.StreamingVideoFiap.utils;

import com.StreamingVideoFiap.controller.form.VideoForm;

import java.util.Random;
import java.util.UUID;

public abstract class VideoHelper {

    public static VideoForm geraVideForm() {
        return VideoForm.builder()
                .titulo(geraPalavraRandomica(10))
                .descricao(geraPalavraRandomica(15))
                .url(geraPalavraRandomica(9))
                .dataPublicacao("01/01/1900")
                .categoria(geraPalavraRandomica(5))
                .build();
    }

    private static String geraPalavraRandomica(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            word.append(randomChar);
        }
        return word.toString();
    }

}
