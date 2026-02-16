package com.easymed.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.easymed.Controller.AuthenticationController;
import com.easymed.Model.Patient;
import com.easymed.Model.User;
import com.easymed.Repository.PatientRepository;

//import lombok.RequiredArgsConstructor;

@Service
public class PatientService {

	private final PatientRepository patientRepository;
	
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}
	
	public Optional<Patient> findById(int id){
		return patientRepository.findById(id);
	}
	
	public Patient findByUser(User user) {
		return patientRepository.findByUser(user);
	}
	
	
}


