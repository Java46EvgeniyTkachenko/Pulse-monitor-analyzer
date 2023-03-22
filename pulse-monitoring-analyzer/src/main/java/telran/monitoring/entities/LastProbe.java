package telran.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class LastProbe {
	@Id
	long patientId;
	int value;
	public LastProbe(long patientId, int value) {
		this.patientId = patientId;
		this.value = value;
	}
	public LastProbe() {
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public long getPatientId() {
		return patientId;
	}
}
