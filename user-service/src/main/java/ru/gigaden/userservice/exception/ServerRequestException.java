package ru.gigaden.userservice.exception;

import lombok.Getter;

@Getter
public class ServerRequestException extends BaseException {
    private final String reason = "Запрашиваемый объект не найден";

    public ServerRequestException(String message) {
        super(message);
    }
}