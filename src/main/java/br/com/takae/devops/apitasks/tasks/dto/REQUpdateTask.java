package br.com.takae.devops.apitasks.tasks.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class REQUpdateTask {
    @NotNull(message = "'id' cannot be null")
    private long id;
    @NotEmpty(message = "'name' cannot be empty")
    private String name;
    private String content;
    private Integer percentage;
}
