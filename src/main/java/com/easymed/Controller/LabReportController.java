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

import com.easymed.Model.LabReport;
import com.easymed.Model.Patient;
import com.easymed.Service.LabReportService;
import com.easymed.Service.PatientService;

@Controller
@RequestMapping("/labReport")
public class LabReportController {
	
	private final LabReportService labReportService;
	private final PatientService patientService;
	
	public LabReportController (LabReportService labReportService, PatientService patientService) {
		this.labReportService = labReportService;
		this.patientService = patientService;
	}
	
	//Show the upload form for a new lab report(UPLOAD FORM)
	@GetMapping("/upload/{patientId}")
	public String showUploadForm(@PathVariable int patientId, Model model) {
		Optional<Patient> patientOpt = patientService.findById(patientId);
		if(patientOpt.isEmpty()) {
			return "error/404";
		}
		
		Patient patient = patientOpt.get();
		
		LabReport report = new LabReport();
		report.setPatient(patient);
		
		model.addAttribute("labReport", report);
		model.addAttribute("patientId", patientId);
		
		return "labreport/upload";   //templates/labreport/upload.html
	}
	
	//Save Report
	@PostMapping("/upload")
	public String uploadReport(@ModelAttribute("labReport") LabReport report) {
		labReportService.saveReport(report);
		return "redirect:/labReport/patient/" + report.getPatient().getId();
	}

	//View all reports for a specific patient
	@GetMapping("/patient/{patientId}")
	public String reportforPatient(@PathVariable int patientId , Model model) {
		List<LabReport> reports = labReportService.findByPatientId(patientId);
		model.addAttribute("labReports", reports);
		return "labreport/list";      //templates/labreport/list/.html
	}
}
