package telran.monitoring;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.model.*;
import telran.monitoring.service.AnalyzerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class) //loading to Application Context producer/consumer for Spring Cloud Messaging
public class AnalyzerControllerTest {
	private static final long PATIENT_ID_NO_JUMP = 123;
	private static final int VALUE = 100;
	private static final long PATIENT_ID_JUMP = 125;
	private static final int VALUE_JUMP = 240;
	@MockBean
	AnalyzerService service;
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	PulseProbe probeNoJump = new PulseProbe(PATIENT_ID_NO_JUMP,0,0, VALUE);
	PulseProbe probeJump = new PulseProbe(PATIENT_ID_JUMP,0,0, VALUE);
	PulseJump jump = new PulseJump(PATIENT_ID_JUMP, VALUE, VALUE_JUMP);
	
	String bindingNameProducer = "pulseProbeConsumer-in-0";
	@Value("${app.jumps.binding.name}")
	String bindingNameConsumer;
	@BeforeEach
	void mockingService() {
		when(service.processPulseProbe(probeJump)).thenReturn(jump);
		when(service.processPulseProbe(probeNoJump)).thenReturn(null);
	}
@Test
void receivingProbNoJump() {
	producer.send(new GenericMessage<PulseProbe>(probeNoJump), bindingNameProducer);
	Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
	assertNull(message);
	
	
}
@Test
void receivingProbJump() throws StreamReadException, DatabindException, IOException {
	producer.send(new GenericMessage<PulseProbe>(probeJump), bindingNameProducer);
	Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
	assertNotNull(message);
	ObjectMapper mapper = new ObjectMapper();
	PulseJump jump = mapper.readValue(message.getPayload(), PulseJump.class);
	assertEquals(this.jump, jump);
	
	
}

}
