package com.easymed.Controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easymed.Model.Appointment;
import com.easymed.Model.Doctor;
import com.easymed.Model.Patient;
import com.easymed.Model.Prescription;
import com.easymed.Service.AppointmentService;
import com.easymed.Service.PatientService;
import com.easymed.Service.PrescriptionService;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {
	
	private final PrescriptionService prescriptionService;
	private final AppointmentService appointmentService;
	private final PatientService patientService;
	
	public PrescriptionController (PrescriptionService prescriptionService, AppointmentService appointmentService, PatientService patientService) {
		this.prescriptionService = prescriptionService;
		this.appointmentService = appointmentService;
		this.patientService = patientService;
	}
	
	//View prescription by appointment
	@GetMapping("/appointment/{appointmentId}")
	public String viewPrescription(@PathVariable("appointmentId") int appointmentId,  Model model) {
//		Optional<Prescription> prescriptionOpt = prescriptionService.findById(appointmentId);
//		
//		
//		if(prescriptionOpt.isPresent()) {
//			model.addAttribute("prescription", prescriptionOpt.get());
//			return "prescription/view";   //templates/prescription/view.html
//		}else {
//			model.addAttribute("error", "Prescription not found");
//			return "error/404";    
//		}
		
		Prescription prescription = prescriptionService.findByAppointmentId(appointmentId);
		if(prescription == null) {
			model.addAttribute("error", "Prescription not found for this appintment");
			return "error/404";
		}
		
		model.addAttribute("prescription", prescription);
		return "prescription/view";
		
	}
	
	//Show form to write a prescription for an appointment
	@GetMapping("/write/{appointmentId}")
	public String showPrescription(@PathVariable int appointmentId, Model model) {
		Optional<Appointment> appointmentOpt = appointmentService.findByid(appointmentId);
		
		if(appointmentOpt.isEmpty()) {
			model.addAttribute("error", "Appointment not found");
			return "error/404";
		}
		
		Appointment appointment = appointmentOpt.get();	
		if(appointment.getPatient() == null) {
			model.addAttribute("error", "Patient not linked with appointment");
			return "error/404";
		}
		
		Patient patient = appointment.getPatient();
		
		//new
//		System.out.println("DEBUG : appointment from DB id = " + appointment.getId());
//		System.out.println("DEBUG : patient from appointment id = "+ (patient != null ? patient.getId() : "null"));
//		if(patient != null) System.out.println("DEBUG patient name = "+ patient.getName());
//		
		//Create and attach a prescription with both references set 
		Prescription prescription = new Prescription();
		prescription.setAppointment(appointment);
		prescription.setPatient(patient);
		
		model.addAttribute("prescription", prescription);
		
		//also add them separately
//		model.addAttribute("debugAppointment", appointment);
//		model.addAttribute("debugPatient",patient);
		return "prescription/write";
	}
	
	//Add a prescription form
//	@GetMapping("/add")
//	public String addPrescriptionForm(Model model ) {
//		Prescription prescription = new Prescription();
//		prescription.setAppointment(new Appointment());   //ensure nested exists for binding
//		prescription.setPatient(new Patient());
//		model.addAttribute("prescription", prescription);
//		return "prescription/write";   //reuse the same form template
//	}
	
//	@GetMapping("/add")
//	public String addPrescriptionForm(){
//		return "redirect:/doctor/dashboard";
//	}
	
	//Save the prescription submitted by the doctor
	@PostMapping("/save")
	public String savePrescription(@ModelAttribute Prescription prescription, Model model) {
//		
		try {
			if(prescription.getAppointment() == null || prescription.getAppointment().getId() == 0) {
				model.addAttribute("error", "Invalid appointment information.");
				return "error/404";
			}
			
			if(prescription.getPatient() == null || prescription.getPatient().getId() == 0) {
				model.addAttribute("error", "Invalid patient information");
				return "error/404";
			}
			
			int appointmentId = prescription.getAppointment().getId();
			int patientId = prescription.getPatient().getId();
			
			Optional<Appointment> appointmentOpt = appointmentService.findByid(appointmentId);
			Optional<Patient> patientOpt = patientService.findById(patientId);
			
			if(appointmentOpt.isEmpty() || patientOpt.isEmpty()) {
				model.addAttribute("error", "Appointment or Patient not found.");
				return "error/404";
			}
			
//			Doctor loggedInDoctor = appointmentOpt.get().getDoctor();
//			prescription.setDoctor(loggedInDoctor);
//			
			prescription.setAppointment(appointmentOpt.get());
			prescription.setPatient(patientOpt.get());
			
			Prescription saved = prescriptionService.savePrescription(prescription);
			return "redirect:/prescription/appointment/" + saved.getAppointment().getId();
			
		}catch(Exception e) {
			model.addAttribute("error", "Error saving prescription: " + e.getMessage());
			return "error/500";
		}
		
		
	}

}
