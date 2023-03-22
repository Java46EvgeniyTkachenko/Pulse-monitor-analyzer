package telran.monitoring.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "patients")
public class Patient {
@Id
	long id;
String name;
public Patient(long id, String name) {
	this.id = id;
	this.name = name;
}
public long getId() {
	return id;
}
public String getName() {
	return name;
}
public Patient() {
}
}
