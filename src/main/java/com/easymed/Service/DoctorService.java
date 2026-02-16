package com.easymed.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.easymed.Model.Doctor;
import com.easymed.Model.User;
import com.easymed.Repository.DoctorRepository;

//import lombok.RequiredArgsConstructor;

@Service
public class DoctorService {

	private final DoctorRepository doctorRepository;
	
	public DoctorService(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	public Doctor saveDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}
	
	public List<Doctor> getAllDoctors(){
		return doctorRepository.findAll();
	}
	
	public Optional<Doctor> findById(int id){
		return doctorRepository.findById(id);
	}
	
	public Doctor findByUser(User user) {
		return doctorRepository.findByUser(user);
	}
}
