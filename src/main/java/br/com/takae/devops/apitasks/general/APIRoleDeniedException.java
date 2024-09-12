package br.com.takae.devops.apitasks.general;

public class APIRoleDeniedException extends RuntimeException {
    public APIRoleDeniedException(String msg) {
        super(msg);
    }

    public APIRoleDeniedException(String pattern, Object... objects) {
        super(objects == null ? pattern : String.format(pattern, objects));
    }
}
