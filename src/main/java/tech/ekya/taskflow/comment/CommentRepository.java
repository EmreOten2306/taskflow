package tech.ekya.taskflow.comment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @EntityGraph(attributePaths = {"task", "author"})
    List<Comment> findByTaskId(Long taskId);

    @EntityGraph(attributePaths = {"task", "author"})
    Optional<Comment> findCommentWithDetailsById(Long id);
}

