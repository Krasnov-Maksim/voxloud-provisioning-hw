package com.voxloud.provisioning.exception;

import com.voxloud.provisioning.util.Utils;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(DeviceNotFoundException.class)
    protected ResponseEntity<Object> handleDeviceNotFoundException(DeviceNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, ex.getMessage());
        body.put(TIMESTAMP, LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    @ExceptionHandler(UnknownDeviceModelException.class)
    protected ResponseEntity<Object> handleUnknownDeviceModelException(
            UnknownDeviceModelException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, Utils.INTERNAL_SERVER_ERROR);
        body.put(TIMESTAMP, LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(IncompatibleTypeOfDataException.class)
    protected ResponseEntity<Object> handleIncompatibleTypeOfDataException(
            IncompatibleTypeOfDataException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, Utils.INTERNAL_SERVER_ERROR);
        body.put(TIMESTAMP, LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(FragmentParsingException.class)
    protected ResponseEntity<Object> handleFragmentParsingException(
            FragmentParsingException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, Utils.INTERNAL_SERVER_ERROR);
        body.put(TIMESTAMP, LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeExceptionException(RuntimeException ex) {
        log.error(ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(MESSAGE, Utils.INTERNAL_SERVER_ERROR);
        body.put(TIMESTAMP, LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
