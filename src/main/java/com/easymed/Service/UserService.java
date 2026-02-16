package com.easymed.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.easymed.Model.User;
import com.easymed.Repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> findById(int id){
		return userRepository.findById(id);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
