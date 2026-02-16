package com.easymed.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easymed.Model.Doctor;
import com.easymed.Model.User;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
	
	Doctor findByUser(User user);

}
