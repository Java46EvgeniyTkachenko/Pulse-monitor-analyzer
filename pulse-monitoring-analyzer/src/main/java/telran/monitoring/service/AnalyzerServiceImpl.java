package telran.monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.monitoring.entities.LastProbe;
import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.LastProbeRepository;

@Service
public class AnalyzerServiceImpl implements AnalyzerService {
	@Autowired
LastProbeRepository lastProbeRepository;
	@Value("${app.jump.threshold:0.3}")
	double jumpThreshold;
	@Override
	@Transactional
	public PulseJump processPulseProbe(PulseProbe probe) {
		PulseJump res = null;
		LastProbe lastProbe =
				lastProbeRepository.findById(probe.patientId).orElse(null);
		if (lastProbe == null) {
			lastProbe = new LastProbe(probe.patientId, probe.value);
			
		} else if (isJump(lastProbe.getValue(), probe.value)){
			res = new PulseJump(probe.patientId, lastProbe.getValue(), probe.value);
		}
		lastProbe.setValue(probe.value);
		lastProbeRepository.save(lastProbe);
		return res;
		
		
	}
	private boolean isJump(int lastValue, int currentValue) {
		int delta = Math.abs(currentValue - lastValue);
		return delta >= lastValue * jumpThreshold;
	}

}
