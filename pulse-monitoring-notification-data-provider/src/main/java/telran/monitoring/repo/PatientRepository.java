package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.monitoring.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
