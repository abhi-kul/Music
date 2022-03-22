package com.example.music.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHelper {

    @ExceptionHandler(value = {MusicBrainzException.class})
    public ResponseEntity<Object> handle(MusicBrainzException e) {
        return new ResponseEntity<>(create(e.getMessage()), INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {MusicServiceException.class})
    public ResponseEntity<Object> handle(MusicServiceException e) {
        return new ResponseEntity<>(create(e.getMessage()), INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {WikipediaException.class})
    public ResponseEntity<Object> handle(WikipediaException e) {
        return new ResponseEntity<>(create(e.getMessage()), NOT_FOUND);
    }
    @ExceptionHandler(value = {CoverArtArchiveException.class})
    public ResponseEntity<Object> handle(CoverArtArchiveException e) {
        return new ResponseEntity<>(create(e.getMessage()), INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {WikiDataException.class})
    public ResponseEntity<Object> handle(WikiDataException e) {
        return new ResponseEntity<>(create(e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse create(Object message) {
        return ExceptionResponse.builder()
                .message(message)
                .build();
    }


}
