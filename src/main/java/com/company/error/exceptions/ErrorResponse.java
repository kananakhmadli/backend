package com.company.error.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class ErrorResponse {

    private String uuid;
    private String code;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private List<ValidationError> checks;

    public ErrorResponse(HttpStatus status, String message, List<ValidationError> checks) {
        this.uuid = UUID.randomUUID().toString();
        this.code = status.name();
        this.message = message;
        this.checks = checks;
    }

}