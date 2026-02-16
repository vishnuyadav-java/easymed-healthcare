package com.easymed.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easymed.Model.Patient;
import com.easymed.Model.User;
import com.easymed.Service.PatientService;
import com.easymed.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final UserService userService;
	private final PatientService patientService;

    public PatientController(UserService userService, PatientService patientService) {
        this.userService = userService;
        this.patientService = patientService;
    }
	
	//Patient dashboard (for logged-in patient)
	@GetMapping("/dashboard")
	//Get logged-in user from session
	public String patientDashboard(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		//Redirect if not logged in
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		Patient patient = patientService.findByUser(loggedInUser);
		if(patient == null) {
			model.addAttribute("error", "Patient no found.");
			return "error/404";
		}
		
		//Add patient and related info to model
		model.addAttribute("patient", patient);
		model.addAttribute("appointments", patient.getAppointments() != null ? patient.getAppointments() : List.of());
		model.addAttribute("prescriptions", patient.getPrescriptions() != null ? patient.getPrescriptions() : List.of());
		model.addAttribute("labReports", patient.getLabReports() != null ? patient.getLabReports() : List.of());
		return "patient/dashboard";
	}
	
	//View patient by ID (optional route)
	@GetMapping("/{id}")
	public String patientById(@PathVariable int id, Model model) {
		Optional<Patient> patientOpt = patientService.findById(id);
		
		if(patientOpt.isPresent()) {
			model.addAttribute("patient", patientOpt.get());
			return "patient/dashboard";     //templates/patient/dashboard.html
		}else {
			model.addAttribute("error", "Patient not found");
			return "error/404";   
					
		}
		   
	}
	
	//Show update patient form 
	@GetMapping("/{id}/edit")
	public String editPatientForm(@PathVariable int id, Model model) {
		Optional<Patient> patientOpt = patientService.findById(id);
		
		if(patientOpt.isPresent()) {
			model.addAttribute("patient",patientOpt.get());
			return "patient/edit";
		}else {
			model.addAttribute("error", "Patient not found");
			return "error/404";
		}
	}
	
	//Update patient details
	@PostMapping("/{id}/update")
	public String updatePatient(@PathVariable int id, @ModelAttribute("patient") Patient patient, Model model) {
		Optional<Patient> patientOpt = patientService.findById(id);
		
		if(patientOpt.isPresent()) {
			patient.setId(id);
			patientService.savePatient(patient);   //Save update patient
			return "redirect:/patient/dashboard" ;
		}else {
			model.addAttribute("error", "Patient not found");
			return "error/404";
		}
		
		
	}
}
