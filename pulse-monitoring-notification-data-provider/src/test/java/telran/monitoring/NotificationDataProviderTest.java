package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.controller.NotificationDataProviderController;
import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderServiceImpl;
@SpringBootTest
//@WebMvcTest({NotificationDataProviderController.class, DataProviderServiceImpl.class,
//	Doctor})
@AutoConfigureMockMvc

class NotificationDataProviderTest {
@Autowired
	MockMvc mockMvc;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	@Sql(scripts = "DoctorsPatientsVisists.sql")
	void test() throws  Exception {
		String jsonResponse = mockMvc.perform(get("/data/123"))
				.andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		NotificationData notificationData = mapper.readValue(jsonResponse, NotificationData.class);
		assertEquals("doctor2@gmail.com", notificationData.doctorEmail);
		assertEquals("doctor2", notificationData.doctorName);
		assertEquals("Vasya", notificationData.patientName);
		
	}

}
