package br.com.takae.devops.apitasks.general;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String developerMessage;
}
