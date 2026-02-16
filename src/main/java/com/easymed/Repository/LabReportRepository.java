package com.easymed.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easymed.Model.LabReport;

public interface LabReportRepository extends JpaRepository<LabReport, Integer>{
	
	List<LabReport> findByPatient_id(int patientId);

}
