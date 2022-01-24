package de.tuhh.diss.project.exceptions;

public class CrashDetectionException extends Exception {

	/**
	 * This exception is thrown incase the robot is going to crash with a wall
	 * during motion
	 */
	private static final long serialVersionUID = 0;

	public CrashDetectionException(String msg) {
		super(msg);
	}

}
