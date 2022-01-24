package de.tuhh.diss.project.tools;

import de.tuhh.diss.project.exceptions.CrashDetectionException;
import de.tuhh.diss.project.exceptions.NeartoCellException;
import de.tuhh.diss.project.exceptions.NoColorDetectionException;
import de.tuhh.diss.project.model.Robot;
import de.tuhh.diss.project.sensors.ColorSensor;
import de.tuhh.diss.project.sensors.GyroScopeSensor;
import de.tuhh.diss.project.sensors.UltraSonicSensor;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public abstract class Follower extends Robot {
	/*
	 * this class is the backbone of all project solvers such as wall solver
	 */

	public final int SOLVING_TIME = 5 * 3600 * 1000; // 5 min to ms;
	private ColorSensor colorSensor;
	private GyroScopeSensor gyroSensor;
	private UltraSonicSensor ultraSensor;
	private int targetColor;

	public Follower() {
		this.initiate_robot();
		this.colorSensor = new ColorSensor();
		this.gyroSensor = new GyroScopeSensor();
		this.ultraSensor = new UltraSonicSensor();
		this.targetColor = this.chooseColor();
		this.setSpeed(550);
		this.beep();

	}

	public void simpleTurn(Direction direction) {
		/*
		 * this function turns the robot 90 degree in the given direction either CW or
		 * CCW, the delay time is calculated according to the shown formula below but I
		 * adjust it alittle bit to give much accurate result
		 */
		motorTurningDirection(direction);
		Delay.msDelay(1000); // time = d / v >> d = [0.25 *(2 PI robotaxis/2)] , v = robotSpeed ,
	}

	public int detectColor() {
		/*
		 * this function returns 3 values: (0) >> if there is no wall infront of the
		 * robot, to detect open ways (1) >> if the wall color = target color (-1) >> if
		 * the wall color != target color
		 */
		float d = this.ultraSensor.getDistance();

		if (d > 0.3) {
			return 0;
		}

		while (d > 0.08) {
			this.moveForward();
			d = this.ultraSensor.getDistance();

		}

		int colorID = this.colorSensor.getColor_ID();
		try {
			this.colorSensor.displayColor(colorID, this.targetColor);
			this.stop();
			return 1;
		} catch (NoColorDetectionException e) {
			this.moveBackward();
			Delay.msDelay(550);
			return -1;
		}

	}

	public void simpleMoveCell() throws CrashDetectionException, NeartoCellException {

		/*
		 * this function simply moves the robot for one cell without any feedback from
		 * sensors, it just delay time required for that, you can consider it as an open
		 * loop control
		 */

		float d = this.ultraSensor.getDistance();
		// in case the robot it is near to a wall, before start moving
		if (d < 0.1) {
			throw new NeartoCellException("robot is too near to obstacle");
		}

		int del = 690; // the required delay time to move a cell distance

		for (int i = 0; i < 4; i++) {
			this.moveForward();
			Delay.msDelay(del);
			d = this.ultraSensor.getDistance();
			LCD.clear();
			LCD.drawString(d + "", 3, 3);

			// in case the robot it is near to wall, during motion
			if (d < 0.12) { // (best 0.12,0.08)
				throw new CrashDetectionException("robot is going to crash");
			}
		}

	}

	public void gyroTurn(int degrees) {

		/*
		 * this function is used also to turn the robot but using a feedback from the
		 * gyroscope sensor
		 */

		Direction CW = degrees > 0 ? Direction.CW : Direction.CCW;
		this.gyroSensor.reset();
		double measured_angle = this.gyroSensor.getFeedBackAngle();

		while (Math.abs(measured_angle) < degrees - 13) {
			measured_angle = this.gyroSensor.getFeedBackAngle();
			this.motorTurningDirection(CW);
			LCD.drawString("angle: " + measured_angle, 1, 1);
		}

	}

	public void cellMove(double distance) throws NeartoCellException, CrashDetectionException { // distance in cm
		/*
		 * anthor moving function using a feedback from the tacho count of the robot
		 * right motor
		 */

		float d = this.ultraSensor.getDistance();
		// in case the robot it is near to a wall, before start moving
		if (d < 0.1) {
			throw new NeartoCellException("robot is too near to obstacle");
		}

		double alpha_motor = (180 * distance * 3) / (Math.PI * this.getWheelRadius());

		while (this.getRightMotor().getTachoCount() < alpha_motor) {
			this.moveForward();
			d = this.ultraSensor.getDistance();
			if (d < 0.12) {
				throw new CrashDetectionException("robot is going to crash");
			}
		}

	}

	public UltraSonicSensor getUltraSensor() {
		return this.ultraSensor;
	}

}