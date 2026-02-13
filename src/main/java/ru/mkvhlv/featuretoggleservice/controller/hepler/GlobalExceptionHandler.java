package ru.mkvhlv.featuretoggleservice.controller.hepler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mkvhlv.featuretoggleservice.controller.dto.ResponseFeature;
import ru.mkvhlv.featuretoggleservice.exceptions.FeatureAlreadyExistsException;
import ru.mkvhlv.featuretoggleservice.exceptions.FeatureNotFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeatureNotFoundException.class)
    public ResponseEntity<ResponseFeature<?>> helperResourceNotFound(FeatureNotFoundException ex) {
        ResponseFeature<Object> body = ResponseFeature.builder()
                .status("Error")
                .responseMessage(ex.getMessage())
                .body(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(FeatureAlreadyExistsException.class)
    public ResponseEntity<ResponseFeature<?>> helperResponseAlreadyExist(FeatureAlreadyExistsException ex) {
        ResponseFeature<Object> body = ResponseFeature.builder()
                .status("Error")
                .responseMessage(ex.getMessage())
                .body(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
