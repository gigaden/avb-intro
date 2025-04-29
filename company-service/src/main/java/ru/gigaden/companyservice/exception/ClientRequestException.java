package ru.gigaden.companyservice.exception;

import lombok.Getter;

@Getter
public class ClientRequestException extends  BaseException {
    private final String reason = "Запрашиваемый объект не найден";

    public ClientRequestException(String message) {
        super(message);
    }
}