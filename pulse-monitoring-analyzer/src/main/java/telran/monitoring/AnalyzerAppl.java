package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import telran.monitoring.model.*;
import telran.monitoring.service.AnalyzerService;
 
@SpringBootApplication
public class AnalyzerAppl {
	@Autowired
AnalyzerService analyzerService;
	@Autowired
	StreamBridge streamBridge;
	@Value("${app.jumps.binding.name:jumps-out-0}")
	private String bindingName;
	public static void main(String[] args) {
		SpringApplication.run(AnalyzerAppl.class, args);

	}
	@Bean
	Consumer<PulseProbe> pulseProbeConsumer() {
		return this::pulseProbeAnalyzing;
	}
	void pulseProbeAnalyzing(PulseProbe pulseProbe) {
		PulseJump pulseJump = analyzerService.processPulseProbe(pulseProbe);
		if(pulseJump != null) {
			streamBridge.send(bindingName, pulseJump);
		}
		
	}

}
