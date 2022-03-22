package com.company.error.exceptions;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class ServiceException extends RuntimeException {

    private String errorUuid;
    private String errorCode;
    private String errorMessage;
    private Map<String, Object> properties = new HashMap<>();

    public ServiceException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorUuid = UUID.randomUUID().toString();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String formatProperties() {
        return Optional.ofNullable(properties)
                .map(Map::keySet)
                .orElse(Collections.emptySet())
                .stream()
                .map(this::formatProperty)
                .collect(Collectors.joining(", "));
    }

    private String formatProperty(String key) {
        return key + ": " + properties.get(key);
    }

}