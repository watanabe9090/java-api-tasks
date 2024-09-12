package br.com.takae.devops.apitasks.tasks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByAccountUsernameAndId(String username, Long id);
    Page<Task> findAllByAccountUsername(String username, Pageable pageable);
}
