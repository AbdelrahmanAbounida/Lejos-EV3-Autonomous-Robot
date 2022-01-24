package de.tuhh.diss.project.exceptions;

public class NoColorDetectionException extends Exception {

	/**
	 * This exception is thrown incase the detected color is not equal to the target
	 * color
	 */
	private static final long serialVersionUID = 0;

	public NoColorDetectionException(String msg) {
		super(msg);
	}

}
