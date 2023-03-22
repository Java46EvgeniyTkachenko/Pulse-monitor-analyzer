package telran.monitoring.service;

import telran.monitoring.model.NotificationData;

public interface DataProviderService {
NotificationData getNotificationData(long patientId);
}
