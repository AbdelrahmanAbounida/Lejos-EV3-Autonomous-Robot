package de.tuhh.diss.project.exceptions;

public class NeartoCellException extends Exception {

	/**
	 * This exception is thrown incase the robot is too near to cell before moving,
	 * it is used to detect the closed corner
	 */
	private static final long serialVersionUID = 0;

	public NeartoCellException(String msg) {
		super(msg);
	}

}
