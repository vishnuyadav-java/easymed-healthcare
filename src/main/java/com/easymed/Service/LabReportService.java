package com.easymed.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.easymed.Model.LabReport;
import com.easymed.Repository.LabReportRepository;

@Service
public class LabReportService {

	private final LabReportRepository labReportRepository;
	
	public LabReportService (LabReportRepository labReportRepository) {
		this.labReportRepository = labReportRepository;
		
	}
	
	public LabReport saveReport(LabReport report) {
		return labReportRepository.save(report);
	}
	
	public Optional<LabReport> findById(int id){
		return labReportRepository.findById(id);
	}
	
	public List<LabReport> findByPatientId(int patientId){
		return labReportRepository.findByPatient_id(patientId);
	}
}
