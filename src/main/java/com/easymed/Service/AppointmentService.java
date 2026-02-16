package com.easymed.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.easymed.Model.Appointment;
import com.easymed.Repository.AppointmentRepository;

@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	
	public AppointmentService(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}
	
	public Appointment bookAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
	
	public List<Appointment> getAppointmentByDoctor(int doctorId){
		return appointmentRepository.findByDoctorId(doctorId);
	}
	
	public List<Appointment> getAppointmentByPatient(int patientId){
		return appointmentRepository.findByPatientId(patientId);
	}
	
	public Optional<Appointment> findByid(int id){
		return appointmentRepository.findById(id);
	}
	
	public void cancelAppointment(int id) {
		appointmentRepository.deleteById(id);
	}
}
