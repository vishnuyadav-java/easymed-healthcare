package com.easymed.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easymed.Model.Patient;
import com.easymed.Model.User;

public interface PatientRepository extends JpaRepository<Patient, Integer>{
	
	Patient findByUser(User user);

}
