package br.com.takae.devops.apitasks.tasks;

import br.com.takae.devops.apitasks.general.APIBadRequestException;
import br.com.takae.devops.apitasks.general.APIRoleDeniedException;
import br.com.takae.devops.apitasks.general.AuthUtils;
import br.com.takae.devops.apitasks.tasks.dto.REQDeleteTask;
import br.com.takae.devops.apitasks.tasks.dto.REQSaveTask;
import br.com.takae.devops.apitasks.tasks.dto.REQUpdateTask;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    private ResponseEntity<Page<Task>> getAllTasks(
            @RequestHeader("X-Auth-Username") String username,
            @RequestHeader(value = "X-Auth-Role", required = false) String role,
            Pageable pageable
    ) {
        if (!AuthUtils.hasNecessaryRole("USER", role)) {
            throw new APIRoleDeniedException();
        }
        Page<Task> tasks = service.getAll(username, pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Void> saveTask(
            @RequestHeader("X-Auth-Username") String username,
            @RequestHeader(value = "X-Auth-Role", required = false) String role,
            @RequestBody REQSaveTask dto
    ) {
        if (!AuthUtils.hasNecessaryRole("USER", role)) {
            throw new APIRoleDeniedException();
        }
        service.save(username, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<Void> updateTask(
            @RequestHeader("X-Auth-Username") String username,
            @RequestHeader(value = "X-Auth-Role", required = false) String role,
            @RequestBody REQUpdateTask dto
    ) throws Exception {
        if (!AuthUtils.hasNecessaryRole("USER", role)) {
            throw new APIRoleDeniedException();
        }
        service.update(username, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    private ResponseEntity<Void> deleteTask(
            @RequestHeader("X-Auth-Username") String username,
            @RequestHeader(value = "X-Auth-Role", required = false) String role,
            @RequestBody REQDeleteTask dto
    ) {
        if (!AuthUtils.hasNecessaryRole("USER", role)) {
            throw new APIRoleDeniedException();
        }
        service.delete(username, dto.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = APIRoleDeniedException.class)
    public ResponseEntity<Void> handleAPIRoleDeniedException(APIRoleDeniedException ex) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = APIBadRequestException.class)
    public ResponseEntity<Void> handleAPIBadRequestException(APIBadRequestException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
