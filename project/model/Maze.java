package de.tuhh.diss.project.model;

import lejos.robotics.Color;

public abstract class Maze {
	/*
	 * this class is used only to define the map environment parameters
	 */
	private final int TILE_SIZE = 35; // this size is in cm
	private final int MAX_SIZE = 4; // this means the max maze size is 4x4 tiles
	private Color targetColor;

	public int getTileSize() {
		return this.TILE_SIZE;
	}

	public int getMaxSize() {
		return this.MAX_SIZE;
	}

	public Color getTargetColor() {
		return this.targetColor;
	}

}
