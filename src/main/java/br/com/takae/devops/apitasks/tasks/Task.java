package br.com.takae.devops.apitasks.tasks;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private long id;
    private String accountUsername;
    private String name;
    private String content;
    private int percentage;
    private Date createdAt;
    private Date modifiedAt;
}
