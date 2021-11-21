package com.company.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestErrorResponse {
    private String uuid;
    private String code;
    private String message;
    private List<ValidationError> checks;
    private Map<String, Object> properties;

    public RestErrorResponse(String uuid, HttpStatus status, String message) {
        this.uuid = uuid;
        this.code = status.name();
        this.message = message;
    }

    public RestErrorResponse(String uuid, String code, String message) {
        this.uuid = uuid;
        this.code = code;
        this.message = message;
    }

    public RestErrorResponse(String uuid, String code, String message, List<ValidationError> checks) {
        this.uuid = uuid;
        this.code = code;
        this.message = message;
        this.checks = checks;
    }

    public RestErrorResponse(String uuid, HttpStatus httpStatus, List<ValidationError> checks) {
        this.uuid = uuid;
        this.code = httpStatus.name();
        this.message = httpStatus.getReasonPhrase();
        this.checks = checks;
    }

    public RestErrorResponse(String uuid, String code, String message, Map<String, Object> properties) {
        this(uuid, code, message);
        this.properties = properties;
    }
}