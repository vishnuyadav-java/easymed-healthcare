package com.easymed.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easymed.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{
	
	Prescription findByAppointmentId(int appointmentId);

}
