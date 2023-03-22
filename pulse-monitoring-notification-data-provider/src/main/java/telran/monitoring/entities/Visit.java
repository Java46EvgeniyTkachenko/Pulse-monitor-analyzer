package telran.monitoring.entities;
import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name="visits", indexes= {@Index(columnList = "patient_id")})
public class Visit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	LocalDate date;
	@ManyToOne
	@JoinColumn(name = "doctor_email")
	Doctor doctor;
	@JoinColumn(name = "patient_id")
	@ManyToOne
	Patient patient;
	public Visit(LocalDate date, Doctor doctor, Patient patient) {
		this.date = date;
		this.doctor = doctor;
		this.patient = patient;
	}
	public Visit() {
	}
	public long getId() {
		return id;
	}
	public LocalDate getDate() {
		return date;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public Patient getPatient() {
		return patient;
	}
	

}
