package telran.monitoring.service;

import org.springframework.stereotype.Service;

import telran.monitoring.model.NotificationData;
import telran.monitoring.repo.*;
@Service
public class DataProviderServiceImpl implements DataProviderService {
	DoctorRepository doctorRepository;
	PatientRepository patientRepository;
	VisitRepository visitRepository;
	@Override
	public NotificationData getNotificationData(long patientId) {
		String doctorEmail = visitRepository.getDoctorEmail(patientId);
		String doctorName = doctorRepository.findById(doctorEmail).get().getName();
		String patientName = patientRepository.findById(patientId).get().getName();
		return new NotificationData(doctorEmail, doctorName, patientName);
	}
	public DataProviderServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository,
			VisitRepository visitRepository) {
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.visitRepository = visitRepository;
	}

}
