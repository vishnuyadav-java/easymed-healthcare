package com.easymed.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easymed.Model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByEmail(String email);

}
