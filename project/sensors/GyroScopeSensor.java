package de.tuhh.diss.project.sensors;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroScopeSensor extends EV3GyroSensor {

	public GyroScopeSensor() {
		super(SensorPort.S3);

	}

	public float getFeedBackAngle() {

		int sampleSize = this.getAngleMode().sampleSize();
		float[] sample = new float[sampleSize];
		this.getAngleMode().fetchSample(sample, 0);
		return sample[0];
	}

}
