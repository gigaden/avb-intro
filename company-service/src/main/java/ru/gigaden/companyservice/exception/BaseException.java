package ru.gigaden.companyservice.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private String reason;

    public BaseException(String message) {
        super(message);
    }
}