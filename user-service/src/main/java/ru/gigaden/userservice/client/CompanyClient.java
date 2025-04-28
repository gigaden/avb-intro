package ru.gigaden.userservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.gigaden.userservice.dto.CompanyResponseDto;
import ru.gigaden.userservice.exception.ClientRequestException;
import ru.gigaden.userservice.exception.ServerRequestException;

import java.util.Optional;

@Service
@Slf4j
public class CompanyClient {

    private final RestClient restClient;

    @Autowired
    public CompanyClient(@Value("${company-service.url}") String baseUrl) {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Optional<CompanyResponseDto> getCompanyById(Long companyId) {
        log.info("Sending request to get the company with id {}", companyId);
        CompanyResponseDto company = restClient.get()
                .uri("/" + companyId)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.warn("Error when sending a request to receive a company by id");
                    throw new ClientRequestException(String.format("Client error: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    log.warn("Server error when getting a company by id");
                    throw new ServerRequestException(String.format("Server error: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                }))
                .body(CompanyResponseDto.class);
        log.info("Response is received");

        return Optional.ofNullable(company);
    }

    public boolean checkCompanyIsExist(Long companyId) {
        log.info("Sending request to check the company with id {} is exist", companyId);
        Boolean isExist = restClient.get()
                .uri("/" + companyId + "/exist")
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.warn("Error when sending a request to receive a company by id");
                    throw new ClientRequestException(String.format("Client error: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    log.warn("Server error when getting a company by id");
                    throw new ServerRequestException(String.format("Server error: %s %s", response.getStatusCode(),
                            response.getHeaders()));
                }))
                .body(Boolean.class);

        return Boolean.TRUE.equals(isExist);
    }
}
