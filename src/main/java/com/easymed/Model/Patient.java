package com.easymed.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



@Entity

public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	private String name;
	private int age;
	private String gender;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<Prescription> prescriptions;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<LabReport> labReports;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public List<LabReport> getLabReports() {
		return labReports;
	}

	public void setLabReports(List<LabReport> labReports) {
		this.labReports = labReports;
	}

	public Patient(int id, String name, int age, String gender, User user, List<Appointment> appointments,
			List<Prescription> prescriptions, List<LabReport> labReports) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.user = user;
		this.appointments = appointments;
		this.prescriptions = prescriptions;
		this.labReports = labReports;
	}

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", user=" + user
				+ ", appointments=" + appointments + ", prescriptions=" + prescriptions + ", labReports=" + labReports
				+ "]";
	}
	
	
	

	

}
