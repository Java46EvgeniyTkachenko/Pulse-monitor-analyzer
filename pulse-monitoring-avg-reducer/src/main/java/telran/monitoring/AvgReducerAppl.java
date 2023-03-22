package telran.monitoring;

import java.util.function.Consumer;


import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import telran.monitoring.model.*;
import telran.monitoring.service.AvgReducerService;

@SpringBootApplication
public class AvgReducerAppl {
	@Autowired
	AvgReducerService service;
	@Autowired
	StreamBridge streamBridge;
	@Value("${app.avg.binding.name}")
	String bindingName;
static Logger LOG = LoggerFactory.getLogger(AvgReducerAppl.class);
	public static void main(String[] args) {
		SpringApplication.run(AvgReducerAppl.class, args);

	}
	@Bean
	Consumer<PulseProbe> pulseProbeConsumer() {
		return this::processPulseProbe;
	}
	void processPulseProbe(PulseProbe probe) {
		Integer avgValue = service.reduce(probe);
		if (avgValue != null) {
			LOG.debug("for patient {} avg value is {}", probe.patientId, avgValue);
			streamBridge.send(bindingName, new PulseProbe(probe.patientId,System.currentTimeMillis(), 0, avgValue));
		} else {
			LOG.trace("for patient {} no avg value yet", probe.patientId);
		}
		
	}
	
}
