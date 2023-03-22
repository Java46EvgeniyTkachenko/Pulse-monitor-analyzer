package telran.monitoring.service;

import telran.monitoring.model.PulseProbe;

public interface AvgReducerService {
Integer reduce(PulseProbe probe);
}
