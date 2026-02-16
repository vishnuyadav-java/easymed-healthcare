package com.easymed.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easymed.Model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
	
	List<Appointment> findByDoctorId(int doctorId);
	List<Appointment> findByPatientId(int patientId);

}
