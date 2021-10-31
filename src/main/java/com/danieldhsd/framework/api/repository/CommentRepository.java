package com.danieldhsd.framework.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieldhsd.framework.api.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
