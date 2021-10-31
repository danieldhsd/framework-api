package com.danieldhsd.framework.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danieldhsd.framework.api.model.Comment;
import com.danieldhsd.framework.api.model.Post;
import com.danieldhsd.framework.api.model.User;
import com.danieldhsd.framework.api.repository.CommentRepository;
import com.danieldhsd.framework.api.repository.PostRepository;
import com.danieldhsd.framework.api.repository.UserRepository;
import com.danieldhsd.framework.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
	    return userRepository.findById(id)
	    		.map(user -> ResponseEntity.ok().body(user))
	    		.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public User create(@RequestBody User user) throws Exception {
	    return userService.create(user);
	}
	
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User newUser) {
		return userRepository.findById(id)
				.map(storedUser -> {
					storedUser.setName(newUser.getName());
					storedUser.setEmail(newUser.getEmail());
					storedUser.setPassword(newUser.getPassword());
					return ResponseEntity.ok().body(userRepository.save(storedUser));
				
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    return userRepository.findById(id)
    			.map(user -> {
    				userRepository.deleteById(id);
    				return ResponseEntity.noContent().build();
    			
    			}).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/{id}/posts")
	public List<Post> findAllPostsByIdUser(@PathVariable Long id) {
		return postRepository.findAllByIdUser(id);
	}
	
	@DeleteMapping("/{id}/posts/{postId}")
	public ResponseEntity<?> deletePostById(@PathVariable Long id, @PathVariable Long postId) {
		Optional<Post> postOptional = postRepository.findById(id);

		if(!postOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Post post = postOptional.get();
		
		if(!post.getPostCreator().getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		postRepository.deleteById(post.getId());
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long id, @PathVariable Long commentId) throws Exception {
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		
		if(!commentOptional.isPresent()) {
			throw new Exception("Comentário não encontrado!");
		}
		
		Comment comment = commentOptional.get();
		if(!id.equals(comment.getCommentCreator().getId())) {
			throw new Exception("Você não pode apagar esse comentário!");
		}
		
		commentRepository.delete(comment);
		
		return ResponseEntity.noContent().build();
	}
}
