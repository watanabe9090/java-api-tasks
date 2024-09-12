package br.com.takae.devops.apitasks.tasks.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class REQSaveTask {
    @NotNull(message = "'name' cannot be null")
    @NotEmpty(message = "'name' cannot be empty")
    private String name;
    private String content;
}
