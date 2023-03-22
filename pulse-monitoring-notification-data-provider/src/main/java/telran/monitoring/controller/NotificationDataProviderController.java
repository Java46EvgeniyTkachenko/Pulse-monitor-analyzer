package telran.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderService;

@RestController
@RequestMapping("data")
public class NotificationDataProviderController {
	@Autowired
	DataProviderService dataProvider;
	@GetMapping("/{patientId}")
	NotificationData getNotificationData(@PathVariable long patientId) {
		return dataProvider.getNotificationData(patientId);
	}
}
