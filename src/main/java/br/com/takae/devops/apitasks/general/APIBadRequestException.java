package br.com.takae.devops.apitasks.general;

public class APIBadRequestException extends RuntimeException {
    public APIBadRequestException(String msg) {
        super(msg);
    }

    public APIBadRequestException(String pattern, Object... objects) {
        super(objects == null ? pattern : String.format(pattern, objects));
    }
}
