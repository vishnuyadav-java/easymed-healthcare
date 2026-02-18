package com.easymed.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easymed.Model.Appointment;
import com.easymed.Model.Doctor;
import com.easymed.Model.Patient;
import com.easymed.Model.User;
import com.easymed.Service.AppointmentService;
import com.easymed.Service.DoctorService;
import com.easymed.Service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	
	private final AppointmentService appointmentService;
	private final PatientService patientService;
	private DoctorService doctorService;
	
	public AppointmentController(AppointmentService appointmentService, PatientService patientService, DoctorService doctorService) {
		this.appointmentService = appointmentService;
		this.patientService = patientService;
		this.doctorService = doctorService;
	}
	
	//Open Appointment page
	@GetMapping
	public String openAppointmentPage(HttpSession session) {
		
		User loggedInUser =(User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		String role = loggedInUser.getRole();
		
		if("PATIENT".equals(role)) {
			
			Patient patient = patientService.findByUser(loggedInUser);
			if(patient != null) {
				return "redirect:/appointment/patient/" + patient.getId();
			}
		}
		
		if("DOCTOR".equals(role)) {
			
			Doctor doctor = doctorService.findByUser(loggedInUser);
			if(doctor != null) {
				return "redirect:/appointment/doctor/" + doctor.getId();
			}
		}
		
		return "error/404";
		
	}

	//Show form to book a new appointment 
	@GetMapping("/book")
	public String showBookingFrom(Model model, HttpSession session) {
		User loggedInUser =(User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		Patient patient = patientService.findByUser(loggedInUser);
		if(patient == null) {
			model.addAttribute("error", "Patient not found.");
			return "error/404";
		}
		
		List<Doctor> doctors = doctorService.getAllDoctors();
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("doctors", doctors);
		return "appointment/book";    //templates/appointments/book.html
	}
	
	//Handle appointment booking form submission
	@PostMapping("/book")
	public String bookAppointment(@ModelAttribute("appointment") Appointment appointment, @RequestParam("doctorId") int doctorId, HttpSession session, Model model) {
		User loggedInUser =(User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		Patient patient = patientService.findByUser(loggedInUser);
		if(patient == null) {
			model.addAttribute("error", "Patient not found.");
			return "error/404";
		}
		
		//Fetch doctor using the select ID
		Optional<Doctor> optionalDoctor = doctorService.findById(doctorId);
		if(optionalDoctor.isEmpty()) {
			model.addAttribute("error", "Invalid doctor selected.");
		}
		
		//Set details before saving
		appointment.setPatient(patient);
		
		Doctor doctor = optionalDoctor.get();
		appointment.setDoctor(doctor);    //extract the doctor object
		
		if(appointment.getStatus() == null || appointment.getStatus().isEmpty()) {
			appointment.setStatus("Scheduled");
		}
		
		if(appointment.getDatetime() == null) {
			appointment.setDatetime(LocalDateTime.now().plusDays(1));   //Default: tomorrow
		}
		
		//Save appointment
		appointmentService.bookAppointment(appointment);
		
		//Redirect back to patient's appointment list page 
		return "redirect:/patient/dashboard" ;
	}

	//Show all appointments for a specific patient
	@GetMapping("/patient/{patientId}")
	public String ViewappointmentsForPatient(@PathVariable("patientId") int patientId, Model model) {
		List<Appointment> appointments = appointmentService.getAppointmentByPatient(patientId);
		
		if(appointments == null) {
			return "error/404";
		}
		
		model.addAttribute("appointments", appointments);
		model.addAttribute("role", "PATIENT");
		return "appointment/view";    //tamplates/appointment/view.html
	}
	
	//Show all appointments for a specific 
	@GetMapping("/doctor/{doctorId}")
	public String viewAppointmentForDoctor(@PathVariable("doctorId") int doctorId, Model model ) {
		List<Appointment> appointments = appointmentService.getAppointmentByDoctor(doctorId);
		
		if(appointments == null) {
			return "error/404";
		}
		
		model.addAttribute("appointments", appointments);
		model.addAttribute("role", "DOCTOR");
		return "appointment/view";
	}

	//Cancel an appointment by ID
	@GetMapping("/{id}/cancel")
	public String cancelAppointment(@PathVariable("id") int id) {
		appointmentService.cancelAppointment(id);
		//Redirect to homepage or dashboard after cancellation
		return "redirect:/patient/dashboard";
	}
}
