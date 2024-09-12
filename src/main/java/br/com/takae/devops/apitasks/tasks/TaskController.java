package br.com.takae.devops.apitasks.tasks;

import br.com.takae.devops.apitasks.general.*;
import br.com.takae.devops.apitasks.tasks.dto.REQDeleteTask;
import br.com.takae.devops.apitasks.tasks.dto.REQSaveTask;
import br.com.takae.devops.apitasks.tasks.dto.REQUpdateTask;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    private ResponseEntity<Page<Task>> getAllTasks(
            @RequestHeader("X-Auth-Username") String username,
            @RequestHeader(value = "X-Auth-Role", required = false) String role,
            @PageableDefault(sort = {"id"}) Pageable pageable
    ) throws BadRequestException {
        if (!AuthUtils.hasNecessaryRole("USER", role)) {
            throw new APIRoleDeniedException();
        }
        /**
         * ToDo: Find a way to handle it before coyote.BadRequestException
         */
        List<Sort.Order> invalidSortStatements = PageableUtils.getInvalidSortStatements(pageable, Task.class);
        if(!invalidSortStatements.isEmpty()) {
            throw new BadRequestException(StringUtils.join(invalidSortStatements.stream().map(Sort.Order::getProperty), ","));
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
    public ResponseEntity<ErrorResponse> handleAPIBadRequestException(APIBadRequestException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setCause(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
