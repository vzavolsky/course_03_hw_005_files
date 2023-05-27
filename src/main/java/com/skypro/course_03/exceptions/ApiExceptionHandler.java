package com.skypro.course_03.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = StudentNotFoundException.class)
    public ResponseEntity<?> handleStudentNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = AvatarProcessingException.class)
    public ResponseEntity<?> handleAvatarProcessingException() {
        return ResponseEntity.badRequest().build();
    }
}
