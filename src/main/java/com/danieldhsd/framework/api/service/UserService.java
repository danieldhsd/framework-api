package com.danieldhsd.framework.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danieldhsd.framework.api.model.User;
import com.danieldhsd.framework.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User create(User user) throws Exception {
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		
		if(userOptional.isPresent()) {
			throw new Exception("Este e-mail já está sendo utilizado");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}
}
