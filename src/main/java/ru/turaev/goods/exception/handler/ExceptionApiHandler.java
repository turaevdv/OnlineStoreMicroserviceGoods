package ru.turaev.goods.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.turaev.goods.exception.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler({AccountingNotFoundException.class, DebitingInvoiceNotFoundException.class, IncorrectProductException.class,
            IncorrectDebitingInvoiceException.class, ProductNotFoundException.class, StorehouseNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(BaseException ex) {
        log.warn("An error has occurred. Error message - {}", ex.getMessage());
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", ex.getMessage());
        responseMap.put("exceptionTime", ex.getLocalDateTime());
        return new ResponseEntity<>(responseMap, ex.getStatus());
    }
}
