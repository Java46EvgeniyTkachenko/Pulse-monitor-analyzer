package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import telran.monitoring.entities.LastProbe;
import telran.monitoring.repo.LastProbeRepository;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class AnalyzerServiceTest {
	private static final long PATIENT_NO_REDIS_DATA  = 125;
	private static final long PATIENT_NO_JUMP = 124;
	private static final long PATIENT_JUMP  = 123;

	private static final int PREVIOUS_VALUE = 70;
	private static final int CURRENT_VALUE = 140;

	@Autowired
	InputDestination producer;
		@Autowired
	OutputDestination consumer;
		
	@MockBean
	LastProbeRepository probesRepository;
	
	LastProbe probeJump = new LastProbe(PATIENT_JUMP, CURRENT_VALUE);
	LastProbe probeNoJump = new LastProbe(PATIENT_NO_JUMP,PREVIOUS_VALUE);
	
	String bindingNameProducer = "pulseProbeConsumer-in-0";

	String bindingNameConsumer = "pulseProbeSupplier-out-0";
	
	@BeforeEach
	void mockingService() {
		when(probesRepository.findById(PATIENT_NO_REDIS_DATA)).thenReturn(Optional.ofNullable(null)); 
		when(probesRepository.findById(PATIENT_NO_JUMP)).thenReturn(Optional.of(probeNoJump)); 
		when(probesRepository.findById(PATIENT_JUMP)).thenReturn(Optional.of(probeJump));  		
	}
	
	@Test
	void testProbeJump() {
		producer.send(new GenericMessage<LastProbe>(probeJump), bindingNameProducer);
		Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
		assertNull(message);
	}

}
