package com.danieldhsd.framework.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.danieldhsd.framework.api.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("SELECT p FROM Post p WHERE p.postCreator.id = :id")
	public List<Post> findAllByIdUser(@Param("id") Long id);
}
