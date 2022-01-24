package de.tuhh.diss.project.solvers;

import de.tuhh.diss.project.exceptions.CrashDetectionException;
import de.tuhh.diss.project.exceptions.NeartoCellException;
import de.tuhh.diss.project.tools.Direction;
import de.tuhh.diss.project.tools.Follower;
import de.tuhh.diss.project.tools.Solvable;
import de.tuhh.diss.project.tools.Terminable;
import lejos.hardware.lcd.LCD;
import lejos.utility.Timer;
import lejos.utility.TimerListener;

public class WallFollower extends Follower implements Solvable, Terminable {

	public WallFollower() {
		super();
	}

	@Override
	public void solveMaze() {
		/*
		 * this is the main solving algorithm where we implement the following algorithm
		 * itself
		 */

		Timer solverTimer = this.initialize_timer(this.SOLVING_TIME);// timer to stop the code after 5 min

		int colState = -2; // this variable defines the output of the color sensor (-1,0,1) as defined in
							// docs
		boolean blockCW = false; // this flag is used to prevent the robot from rotating many times at corners

		try {
			solverTimer.start();
			while (true) {
				colState = this.detectColor(); 
				
				if (!blockCW) {
					this.simpleTurn(Direction.CW); // turn CW 90}
				}
				
				//########## found the color (1) #############//				
				if (colState == 1) { // color found
					this.beep();
					this.stop();
					break;
				}
				
				//##########  wall color != target color (-1) #############//	
				else if (colState == -1) { 
					this.simpleTurn(Direction.CCW); // turn CCW 90
				}
				
				//########## wall is too far >> means an open way (0) #############//	
				else { 
					try {
						blockCW = false;
						this.simpleMoveCell();
						// this.cellMove(0.3); // >> just other implementation of how the robot will move

					} catch (CrashDetectionException e) { // robot is going to crash during motion
						continue; // don't move again

					} catch (NeartoCellException e) { // robot is already near to a cell so it may be at corner

					}
				}
				//##########finally,we take a final decision depending on robot state #############//
				try {
					blockCW = false;
					this.simpleMoveCell(); // robot still in an open way
					// this.cellMove(0.3);
				}

				catch (CrashDetectionException e) { 
					// just forced to do it, but I am sure it won't hapen

				} catch (NeartoCellException e) { 
					// I am at corner and need to rotate anthor time, to prevent redundant actions
					if (colState == -1) {
						blockCW = true;
						this.simpleTurn(Direction.CCW);
					}
				}
			}
		}

		catch (Exception e) {
			// time is up
			this.stop();
		}

	}

	@Override
	public Timer initialize_timer(int time) {

		/*
		 * initialize timer with a delay time = 5 min, after that it will force the
		 * robot to stop
		 */

		Timer timer = new Timer(time, new TimerListener() {

			@Override
			public void timedOut() {
				beep();
				LCD.clear();
				LCD.drawString("Time is Up", 3, 1);

			}
		});
		return timer;
	}

}
