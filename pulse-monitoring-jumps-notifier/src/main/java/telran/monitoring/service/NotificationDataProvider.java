package telran.monitoring.service;

import telran.monitoring.model.NotificationData;

public interface NotificationDataProvider {
NotificationData getData(long patientId);
}
