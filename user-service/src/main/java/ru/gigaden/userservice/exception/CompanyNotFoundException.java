package ru.gigaden.userservice.exception;

import lombok.Getter;

@Getter
public class CompanyNotFoundException extends BaseException {
    private final String reason = "Запрашиваемый объект не найден";

    public CompanyNotFoundException(String message) {
        super(message);
    }
}