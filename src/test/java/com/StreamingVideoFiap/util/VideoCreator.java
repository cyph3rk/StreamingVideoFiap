package com.StreamingVideoFiap.util;

import com.StreamingVideoFiap.model.Video;

public class VideoCreator {

    public static Video createVideoToBeSaved() {
        return new Video("video 90",
                      "descricao 90",
                           "url 90",
                   "01/01/1900");
    }

    public static Video createValidVideo() {
        return new Video("video 91",
                "descricao 91",
                "url 90",
                "01/01/1901");
    }

    public static Video createdValidUpdatedVideo() {
        return new Video("video 92",
                "descricao 92",
                "url 90",
                "01/01/1902");
    }

}
