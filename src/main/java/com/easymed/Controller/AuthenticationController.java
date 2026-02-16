package com.easymed.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.easymed.Model.Doctor;
import com.easymed.Model.Patient;
import com.easymed.Model.User;
import com.easymed.Service.DoctorService;
import com.easymed.Service.PatientService;
import com.easymed.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    private final DoctorService doctorService;

    private final PatientService patientService;
	
	private final UserService userService;
	
	public AuthenticationController(UserService userService, PatientService patientService, DoctorService doctorService) {
		this.userService = userService;
		this.patientService = patientService;
		this.doctorService = doctorService;
	}
	
	//Show login page
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());  //ensure form has a model object
		return "auth/login";    //templates/auth/login.html
	}
	
	//Handle login form submission
	@PostMapping("/login")
	public String processLogin(@ModelAttribute("user") User user, Model model , HttpSession session) {
		
		//Find user by email
		User existingUser =userService.findByEmail(user.getEmail());
		
		//Null check before comparing passwords
		if(existingUser == null) {
			model.addAttribute("error", "Invalid emial or password.");
			return "auth/login";
		}
		
		if(existingUser.getPassword() == null || user.getPassword() == null) {
			model.addAttribute("error", "Password not set. Please contact support.");
			return "auth/login";
		}
		
		//Validate password
		if(existingUser.getPassword().equals(user.getPassword())) {
			
			//Save logged-in user to session
			session.setAttribute("loggedInUser", existingUser);
			
			//Successful login -> redirect to dashboard (decide based on user role)
			if("DOCTOR".equalsIgnoreCase(existingUser.getRole())){
				return "redirect:/doctor/dashboard";
			} else if("PATIENT".equalsIgnoreCase(existingUser.getRole())) {
				return "redirect:/patient/dashboard";
			} else {
				model.addAttribute("error", "Unknown user role.");
				return "auth/login";
			}
			
		}else {
			//failed login ->Incorrect password
			model.addAttribute("error", "Invalid email or password");
			model.addAttribute("user", new User());
			return "auth/login";
			
		}
	}
	
	//Show register page
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "auth/register";    //templates/auth/register.html
	}
	
	//Handle register form submission
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
		
		//Prevent duplicate registration
		if(userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("error", "Email already registered! Please login.");
			return "auth/register";
		}
		
		//Default role (optional)
		if(user.getRole() == null || user.getRole().isEmpty()) {
			user.setRole("PATIENT");
		}
		
		//Save user first
		userService.saveUser(user);
		if("PATIENT".equalsIgnoreCase(user.getRole())) {
			Patient patient = new Patient();
			patient.setUser(user);
			patient.setName(user.getName());   //you can also ask for name in form
			patientService.savePatient(patient);
			
		}
		else if("DOCTOR".equalsIgnoreCase(user.getRole())) {
			Doctor doctor = new Doctor();
			doctor.setUser(user);
			doctor.setName(user.getName());
			doctor.setSpecialization(request.getParameter("specialization"));
			doctor.setAvailability(request.getParameter("availability"));
			doctorService.saveDoctor(doctor);
		}
		model.addAttribute("message", "Registration successful! Please login.");
		return "redirect:/login";
	}
	
	//Logout(simple redirect)
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();   //clear session
		return "redirect:/login";
	}
}
