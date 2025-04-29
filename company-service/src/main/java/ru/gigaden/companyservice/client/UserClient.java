package ru.gigaden.companyservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.gigaden.companyservice.dto.UserResponseDto;
import ru.gigaden.companyservice.exception.ClientRequestException;
import ru.gigaden.companyservice.exception.ServerRequestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserClient {

    private final RestClient restClient;

    @Autowired
    public UserClient(@Value("${user-service.url}") String baseUrl) {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<UserResponseDto> getAllUsersByCompanyId(Long companyId) {
        log.info("Sending request to get the users with companyId {}", companyId);
        UserResponseDto[] users = restClient.get()
                .uri("/company/" + companyId)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.warn("Error when sending a request to receive users by company id");
                    throw new ClientRequestException(String.format("Client error: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    log.warn("Server error when getting a user by id");
                    throw new ServerRequestException(String.format("Server error: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                }))
                .body(UserResponseDto[].class);
        log.info("Response is received");

        return users != null ? Arrays.asList(users) : List.of();
    }
}
