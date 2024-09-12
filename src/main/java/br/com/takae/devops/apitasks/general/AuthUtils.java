package br.com.takae.devops.apitasks.general;

import java.util.Arrays;
import java.util.List;

public class AuthUtils {
    private final static List<String> roles = List.of("USER", "ADMIN");
    public static boolean hasNecessaryRole(String level, String role) {
        return roles.indexOf(role.trim().toUpperCase()) >= roles.indexOf(level);
    }
}
