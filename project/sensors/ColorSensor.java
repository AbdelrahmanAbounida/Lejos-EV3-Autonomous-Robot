package de.tuhh.diss.project.sensors;

import de.tuhh.diss.project.exceptions.NoColorDetectionException;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;

public class ColorSensor extends EV3ColorSensor {

	public ColorSensor() {
		super(SensorPort.S1);

	}

	public void displayColor(int ID, int targetColor) throws NoColorDetectionException {
		/*
		 * if the color detected = target color >> display the color on the LCD before
		 * stopping the robot
		 */

		int col = 5;
		int row = 5;
		if (ID == targetColor) {
			LCD.clear();
			switch (ID) {
			case Color.RED:
				LCD.drawString("RED", col, row);
				break;
			case Color.GREEN:
				LCD.drawString("GREEN", col, row);
				break;
			case Color.BLUE:
				LCD.drawString("BLUE", col, row);
				break;
			default:
				LCD.drawString("", col, row);
				break;
			}
		} else {
			throw new NoColorDetectionException("Color detected not included in the choices");
		}

	}

	public int getColor_ID() {
		SensorMode colorMode = this.getColorIDMode();
		float[] sample = new float[colorMode.sampleSize()];
		colorMode.fetchSample(sample, 0);
		return (int) sample[0];

	}
}
