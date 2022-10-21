package com.move.review.repository;

import com.move.review.domain.Comment;
import com.move.review.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
}
