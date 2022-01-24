package de.tuhh.diss.project.model;

import de.tuhh.diss.project.tools.Direction;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.Color;

public abstract class Robot {
	/*
	 * this class represents the car it self , its main functions
	 */

	private final double WHEEL_RADIUS = 27.5 * Math.pow(10, -3);
	private final double AXIS_LENGTH = 123 * Math.pow(10, -3);
	private final int GEAR_RATIO = 3;
	protected EV3LargeRegulatedMotor engineR;
	protected EV3LargeRegulatedMotor engineL;

	public void initiate_robot() {

		this.engineR = new EV3LargeRegulatedMotor(MotorPort.C);
		this.engineL = new EV3LargeRegulatedMotor(MotorPort.B);

	}

	public EV3LargeRegulatedMotor getRightMotor() {
		return this.engineR;
	}

	public EV3LargeRegulatedMotor getLeftMotor() {
		return this.engineL;
	}

	public double getWheelRadius() {
		return this.WHEEL_RADIUS;
	}

	public double getAxisLength() {
		return AXIS_LENGTH;
	}

	public int getGearRatio() {
		return this.GEAR_RATIO;
	}

	public void setSpeed(int speed) {

		this.engineR.setSpeed(speed);
		this.engineL.setSpeed(speed);
	}

	public void motorTurningDirection(Direction direction) {
		/*
		 * this function rotate the robot according to the given direction
		 */
		if (direction == Direction.CW) {
			getRightMotor().forward();
			getLeftMotor().backward();
		} else {
			getRightMotor().backward();
			getLeftMotor().forward();
		}
	}

	public void moveBackward() {
		this.engineR.forward();
		this.engineL.forward();
	}

	public void moveForward() {
		this.engineR.backward();
		this.engineL.backward();
	}

	public void stop() {
		this.engineR.stop();
		this.engineL.stop();
	}

	public void beep() {
		// Sound.playTone(50,1300,90); //Frequency,Duration,Volume
		Sound.beep();
	}

	public int chooseColor() {
		/*
		 * this function get the target color from the user and saves it
		 */
		showChoices(0);
		int btn_state = 0;
		int arrow = 0;

		while (true) {
			// display the screen
			showChoices(arrow);
			Button.waitForAnyPress();
			LCD.clear();
			// check buttons' state
			btn_state = Button.getButtons();

			switch (btn_state) {

			case 1:
				arrow = (arrow != 0) ? --arrow : arrow;
				break; // up
			case 4:
				arrow = (arrow != 2) ? ++arrow : arrow;
				break; // down
			case 2: // OK
				switch (arrow) {
				case 0:
					return Color.RED;
				case 1:
					return Color.GREEN;
				case 2:
					return Color.BLUE;
				default:
					continue;
				}
			default:
				continue;
			}
		}
	}

	private void showChoices(int arrow) {
		/*
		 * simple display for the available target colors to the user
		 */

		String v = (arrow == 0) ? ">" + "Red" : "Red";
		String f = (arrow == 1) ? ">" + "Green" : "Green";
		String d = (arrow == 2) ? ">" + "Blue" : "Blue";

		LCD.drawString("Choose Color", 5, 0);
		LCD.drawString(v, 1, 1);
		LCD.drawString(f, 1, 2);
		LCD.drawString(d, 1, 3);

	}

}
