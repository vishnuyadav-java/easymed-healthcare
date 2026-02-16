package com.easymed.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity

public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "appointment_id", nullable = false)
	private Appointment appointment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id", nullable = false)
	private Patient patient;
	
	@Lob
	@Column(nullable = false)
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Prescription(int id, Appointment appointment, Patient patient, String content) {
		super();
		this.id = id;
		this.appointment = appointment;
		this.patient = patient;
		this.content = content;
	}

	public Prescription() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Prescription [id=" + id + ", appointment=" + appointment + ", patient=" + patient + ", content="
				+ content + "]";
	} 

}
