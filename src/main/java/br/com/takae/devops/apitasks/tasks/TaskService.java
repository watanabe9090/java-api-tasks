package br.com.takae.devops.apitasks.tasks;

import br.com.takae.devops.apitasks.general.APIBadRequestException;
import br.com.takae.devops.apitasks.tasks.dto.REQSaveTask;
import br.com.takae.devops.apitasks.tasks.dto.REQUpdateTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public Page<Task> getAll(String username, Pageable pageable) {
        return repository.findAllByAccountUsername(username, pageable);
    }

    public Task save(String username, REQSaveTask dto) {
        if(dto.getName() == null || dto.getName().isEmpty() || dto.getName().isBlank()) {
            throw new APIBadRequestException();
        }
        Task newTask = new Task();
        Date createdAt = new Date();
        newTask.setName(dto.getName());
        newTask.setAccountUsername(username);
        newTask.setContent(dto.getContent());
        newTask.setCreatedAt(createdAt);
        newTask.setModifiedAt(createdAt);
        newTask.setPercentage(0);
        newTask = repository.save(newTask);
        log.debug("Save Task {} -> [{}]", newTask.getAccountUsername(), newTask.getName());
        return newTask;
    }

    public Task update(String username, REQUpdateTask dto) throws Exception {
        Optional<Task> optTask = repository.findByAccountUsernameAndId(username, dto.getId());
        if (optTask.isEmpty()) {
            throw new APIBadRequestException();
        }
        Task task = optTask.get();
        if (dto.getName() != null && !dto.getName().isEmpty() && !dto.getName().isBlank()) {
            task.setName(dto.getName());
        }
        if (dto.getContent() != null && !dto.getContent().isEmpty() && !dto.getContent().isBlank()) {
            task.setContent(dto.getContent());
        }
        if (dto.getPercentage() != null) {
            task.setPercentage(dto.getPercentage());
        }
        task.setModifiedAt(new Date());
        Task updatedTask = repository.save(task);
        log.debug("Update Task {} -> [{}]", updatedTask.getAccountUsername(), updatedTask.getName());
        return updatedTask;
    }

    public void delete(String username, long id) {
        log.debug("Deleting Task {} -> id: {}", username, id);
        repository.deleteById(id);
    }
}
