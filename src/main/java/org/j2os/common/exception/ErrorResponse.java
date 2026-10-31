package org.j2os.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private boolean success;
    private String message;
    private int internalCode;
    private Map<String , List<String>> errors;
}
