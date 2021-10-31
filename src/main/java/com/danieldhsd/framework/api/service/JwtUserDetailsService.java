package com.danieldhsd.framework.api.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.danieldhsd.framework.api.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<com.danieldhsd.framework.api.model.User> userOptional = userRepository.findByEmail(email);
		
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		com.danieldhsd.framework.api.model.User user = userOptional.get();
		
		if (user.getEmail().equals(email)) {
			return new User(email, user.getPassword(), new ArrayList<>());
		}
		
		throw new UsernameNotFoundException("User not found with email: " + email);
	}
}
