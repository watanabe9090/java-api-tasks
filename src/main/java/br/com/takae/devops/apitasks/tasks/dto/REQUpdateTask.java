package br.com.takae.devops.apitasks.tasks.dto;

import lombok.Data;

@Data
public class REQUpdateTask {
    private long id;
    private String name;
    private String content;
    private Integer percentage;
}
