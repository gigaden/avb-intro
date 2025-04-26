package ru.gigaden.userservice.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends  BaseException {
    private final String reason = "Запрашиваемый объект не найден";

    public UserNotFoundException(String message) {
        super(message);
    }
}