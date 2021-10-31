package com.danieldhsd.framework.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danieldhsd.framework.api.model.Comment;
import com.danieldhsd.framework.api.model.Post;
import com.danieldhsd.framework.api.repository.CommentRepository;
import com.danieldhsd.framework.api.repository.PostRepository;
import com.danieldhsd.framework.api.util.Utils;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable Long id) {
	    return postRepository.findById(id)
	    		.map(post -> ResponseEntity.ok().body(post))
	    		.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Post create(@RequestBody Post post) {
		return postRepository.save(post);
	}
	
	@PostMapping("/anexo")
	public String anexo(@RequestParam MultipartFile file) throws IOException {
		return Utils.saveFile(file);
	}
	
	@PostMapping("/{id}/comment")
	public Comment addComment(@PathVariable Long id, @RequestBody Comment comment) throws Exception {
		Optional<Post> postOptional = postRepository.findById(id);
		
		if(!postOptional.isPresent()) {
			throw new Exception("Post não existente");
		}
		
		Post post = postOptional.get();
		if(post.getComments() == null) {
			post.setComments(new ArrayList<>());
		}
		
		comment.setPost(post);
		comment = commentRepository.save(comment);
		
		post.getComments().add(comment);
		postRepository.save(post);
		
		return comment;
	}
	
}
