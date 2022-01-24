package de.tuhh.diss.project.sensors;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltraSonicSensor extends EV3UltrasonicSensor {

	public UltraSonicSensor() {
		super(SensorPort.S4);

	}

	public float getDistance() {
		SampleProvider distMode = this.getDistanceMode();
		int sampleSize = distMode.sampleSize();
		float[] sample = new float[sampleSize];
		distMode.fetchSample(sample, 0);
		return sample[0];
	}

}
