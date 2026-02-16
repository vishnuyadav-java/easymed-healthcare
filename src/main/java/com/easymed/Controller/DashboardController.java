package com.easymed.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.easymed.Model.Appointment;
import com.easymed.Model.Doctor;
import com.easymed.Model.LabReport;
import com.easymed.Model.Patient;
import com.easymed.Model.Prescription;
import com.easymed.Model.User;
import com.easymed.Service.AppointmentService;
import com.easymed.Service.DoctorService;
import com.easymed.Service.LabReportService;
import com.easymed.Service.PatientService;
import com.easymed.Service.PrescriptionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
	
	private final DoctorService doctorService;
	private final PatientService patientService;
	private final AppointmentService appointmentService;
	private final LabReportService labReportService;
	private final PrescriptionService prescriptionService;
	
	public DashboardController(
			DoctorService doctorService,
			PatientService patientService,
			AppointmentService appointmentService,
			LabReportService labReportService,
			PrescriptionService prescriptionService) {
		
		this.doctorService = doctorService;
		this.patientService = patientService;
		this.appointmentService = appointmentService;
		this.labReportService = labReportService;
		this.prescriptionService = prescriptionService;
	}
	
	//Show dashboard based on logged-in user role
	@GetMapping("/dashboard")
	public String showDashboard(HttpSession session, Model model) {
		
		//1. Get logged-in user
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null) {
			//No user in session -> redirect to login
			return "redirect:/login";
		}
		
		//2. Handle doctor dashboard
		if("DOCTOR".equalsIgnoreCase(loggedInUser.getRole())) {
			
			Doctor doctor = doctorService.findByUser(loggedInUser);
			if(doctor == null) {
				model.addAttribute("error", "Doctor profile not found.");
				return "error/404";
			}
			
			List<Appointment> appointments = appointmentService.getAppointmentByDoctor(doctor.getId());
			model.addAttribute("doctor", doctor);
			model.addAttribute("appointments", appointments);
			
			return "doctor/dashboard";    //templates/doctor/dashboard.html
			
		}
		
		//3. Handle patient dashboard
		else if("PATIENT".equalsIgnoreCase(loggedInUser.getRole())) {
			
			Patient patient = patientService.findByUser(loggedInUser);
			if(patient == null) {
				model.addAttribute("error", "Patient profile not found.");
				return "error/404";
			}
			
			List<Appointment> appointments = appointmentService.getAppointmentByPatient(patient.getId());
			List<LabReport> labReports = labReportService.findByPatientId(patient.getId());
			
			//Each appointment can have one prescription 
			List<Prescription> prescriptions = appointments.stream()
					.map(a -> prescriptionService.findByAppointmentId(a.getId()))
					.filter(p -> p != null)
					.toList();
			
			model.addAttribute("patient", patient);
			model.addAttribute("appointments", appointments);
			model.addAttribute("labReports", labReports);
			model.addAttribute("prescriptions", prescriptions);
			
			return "patient/dashboard";   //templates/patient/dashboard.html
			
		}
		
		//4. If unknown role
		else {
			model.addAttribute("error", "Unknown user role.");
			return "error/404";
		}
	}

}
