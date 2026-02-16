package com.easymed.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easymed.Model.Appointment;
import com.easymed.Model.Doctor;
import com.easymed.Model.User;
import com.easymed.Service.AppointmentService;
import com.easymed.Service.DoctorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	private final DoctorService doctorService;
	private final AppointmentService appointmentService;
	
	public DoctorController(DoctorService doctorService, AppointmentService appointmentService) {
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}
	
	//List all doctors
	@GetMapping("/list")
	public String listAllDoctor(Model model) {
		List<Doctor> doctors = doctorService.getAllDoctors();
		model.addAttribute("doctors", doctors);
		return "doctor/dashboard";    //templates/doctor/dashboard.html
	}
	
	//Show individual doctor profile
	@GetMapping("/{id}")
	public String doctorProfile(@PathVariable("id") int id,  Model model) {
		Doctor doctor = doctorService.findById(id)
				.orElse(null);
		if(doctor == null) {
			model.addAttribute("error", "Doctor not found");
			return "error/404";  
		}
		
		model.addAttribute("doctor",doctor);
		return "doctor/profile";   //templates/doctor/profile.html
	}
	
	//Doctor Dashboard (for logge-in user)
	@GetMapping("/dashboard")
	public String doctorDashboard(HttpSession session, Model model) {
		User loggedInUser =(User) session.getAttribute("loggedInUser");
		
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		Doctor doctor = doctorService.findByUser(loggedInUser);
		if(doctor == null) {
			model.addAttribute("error", "Doctor profile not found.");
			return "error/404";
		}
		
		//Fetch doctor's appointments
		List<Appointment> appointments = appointmentService.getAppointmentByDoctor(doctor.getId());
		
		//Add to model
		model.addAttribute("doctor", doctor);
		model.addAttribute( "appointments", appointments);
		
		return "doctor/dashboard";   //templates/doctor/dashboard.html
	}
	
}
