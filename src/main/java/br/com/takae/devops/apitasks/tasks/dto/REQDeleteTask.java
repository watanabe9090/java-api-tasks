package br.com.takae.devops.apitasks.tasks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class REQDeleteTask {
    @NotNull(message = "'id' cannot be null")
    private Long id;
}
