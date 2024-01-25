package com.StreamingVideoFiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.ReactorBlockHoundIntegration;

@SpringBootApplication
public class StreamingVideoFiapApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingVideoFiapApplication.class, args);
	}

}
