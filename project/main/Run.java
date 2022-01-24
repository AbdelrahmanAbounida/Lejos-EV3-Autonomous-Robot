package de.tuhh.diss.project.main;

import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import de.tuhh.diss.project.exceptions.CrashDetectionException;
import de.tuhh.diss.project.exceptions.NeartoCellException;
import de.tuhh.diss.project.solvers.WallFollower;

class Run {

	public static void main(String[] args) throws CrashDetectionException, NeartoCellException, InterruptedException {

		MazebotSimulation sim = new MazebotSimulation("mazes/3x4_1.png", 1.4, 1.05);
		GuiMazeVisualization gui = new GuiMazeVisualization(1.7, sim.getStateAccessor());
		sim.scaleSpeed(1);
		sim.setRobotPosition(0.03, 0.17, 90);

		sim.startSimulation();
		gui.startVisualization();

		WallFollower follower = new WallFollower();
		follower.solveMaze();

		// ############# To test the program ############### //
		/*
		 * WallFollower follwer = new WallFollower(); follwer.solveMaze();
		 * 
		 * ## OR ##
		 * 
		 * IntelligentSolver follwer = new IntelligentSolver(); follwer.solveMaze();
		 */

		// sim.stopSimulation();
	}
}