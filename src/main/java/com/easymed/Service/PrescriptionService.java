package com.easymed.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.easymed.Model.Prescription;
import com.easymed.Repository.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	private final PrescriptionRepository prescriptionRepository;
	
	public PrescriptionService (PrescriptionRepository prescriptionRepository) {
		this.prescriptionRepository = prescriptionRepository;
	}
	
	public Prescription savePrescription(Prescription prescription) {
		return prescriptionRepository.save(prescription);
	}
	
	public Optional<Prescription> findById(int id){
		return prescriptionRepository.findById(id);
	}
	
	public Prescription findByAppointmentId(int appointmentId) {
		return prescriptionRepository.findByAppointmentId(appointmentId);
	}

}
