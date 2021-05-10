package com.fhsa.stocks.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
public class Error {

    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
