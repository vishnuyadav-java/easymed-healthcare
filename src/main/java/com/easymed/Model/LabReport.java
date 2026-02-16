package com.easymed.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class LabReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String reportType;
	private String filePath;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LabReport(int id, String reportType, String filePath, Patient patient, Doctor doctor) {
		super();
		this.id = id;
		this.reportType = reportType;
		this.filePath = filePath;
		this.patient = patient;
		this.doctor = doctor;
	}

	public LabReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LabReport [id=" + id + ", reportType=" + reportType + ", filePath=" + filePath + ", patient=" + patient
				+ ", doctor=" + doctor + "]";
	}
	
	

	
}
