package br.com.takae.devops.apitasks.general;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class PageableUtils {
    public static <T> List<Sort.Order> getInvalidSortStatements(Pageable pageable, Class<T> clazz) {
        return pageable.getSort().get()
                .filter(o -> Arrays.stream(clazz.getDeclaredFields())
                        .noneMatch(f -> f.getName().equals(o.getProperty())))
                .toList();
    }
}
