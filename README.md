[![Made with LejosAPI](https://img.shields.io/badge/Made%20with-LejosEV3-orange?style=for-the-badge&logo=Jupyter)](https://lejos.sourceforge.io/ev3/docs/)
[![GitLab](https://img.shields.io/badge/GitLab-330F63?style=for-the-badge&logo=gitlab&logoColor=white)](https://gitlab.com/users/sign_in?__cf_chl_jschl_tk__=LFv2DLZqlpTZrHuh6y0I_PGzdDhipU4jwy_xvbNTOX8-1642896722-0-gaNycGzNCH0)
[![Made with Java](https://img.shields.io/badge/Java-green?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![](https://img.shields.io/badge/API-red?style=for-the-badge)](https://lejos.sourceforge.io/ev3/docs/overview-summary.html)
[![](https://img.shields.io/badge/Eclipse-purple?style=for-the-badge)](https://crates.io/crates/redant)
#  Table of contents 
* [Introduction](#introduction)
* [UML Diagram](#uml_diagram)
* [Packages](#packages)
  * [Model_Package](#model_package)
  * [Sensors_Package](#sensors_package)
  * [Exceptions_Package](#exceptions_package)
  * [Solvers_Package](#solvers_package)
  * [Tools_Package](#tools_package)
  * [Main_Package](#main_package)

<strong> <h1>  Introduction </h1></strong>

This is the final project where EV3Lejos robot will be able to maneuver through no-loop maze to find a specific colorful wall with the latter dertermined by the user out of three choices: Red, Green and Blue. 
and for this I will use some helper classes but before going to the describtion of each class, lets take a look at the UML diagram of this project (class Diagram) 

<strong> <h1> UML_Diagram </h1> </strong>

The following UML class diagram shows the hirarichy of this project and the relation between different classes to implement the required tasks

<img src="Project.jpg" alt="a"/>

<strong><h1> Packages </h1></strong>
Now, lets talk about the hierarchy of the project. The modularization structre of this software consists of 6 packages and each package of these packages has a main role in building the main algorithm. 

<strong><h1> 1-Model Package </h1></strong>

This package contains 2 classes Robot,Maze that represent the following:

<h2> 1-Maze: </h2>

this abstract class is just used to save some final variables related to the maze describtion like the tile size, length of the maze and so on.

<h2> 2-Robot:</h2>

This abstract class can be considered as the robot chassis, electroincs for our maze solver classes [Maze_Solver](#mazesolver_package).Simply, it is the backbone of the follower class. This class itself with its parameters such as wheel radius and axis length , this is responsiable to implement some required tasks such as getting out the sound , showes a list of the choices to the user beside some other important methods in addition, it instansiate the EV3 Motors.and these are a list of important methods inside the robot class:

```java
Robot.moveCellint distance, int speed, float ultrasonicDistance, float safteyDistance);
```
```java
Robot.beep();
```
```java
Robot.show_choices();
```

<strong><h1> 2-Sensors Package </h1></strong>
this package contains 3 classes (Sensors) that represent the following:

<h2> 1-ColorSensor:</h2>

this sensor extends EV3colorSensor so it can be used to detect the required color, so this sensor output can be represented as the main tool of this project as ending the task depends on this sensor output.

<h2> 2-GyroScopeSensor:</h2>

this sensor inherits EV3Gyroscope sensor that can give a feedback about the turning angle of the robot.
undetected output of this sensor like a gray or pink color will throw a [NoColorDetectionException](#exceptions_package)

<h2> 3-UltraSonicSensor:</h2>

this sensor extends EV3UltrasonicSensor sensor that can give a feedback about the front distance between the robot and the wall.
undetected output of this sensor like d < 4 cm will throw a [CrashDetectionException](#exceptions_package)


<strong><h1> 3-Exceptions Package </h1></strong>
this package contains 3 classes (Exceptions) that represent the following:

<h2> 1- CrashDetectionException:</h2>

 which could be thrown during motion when the distance between the robot and the maze walls is less than (saftey distance = 4 cm) and this exception is handled in the solver classes inside maze_solver package

<h2> 2- NoColorDetectionException:</h2>

 this exception will be thrown if the robot detect no color or detect color which does not exist in the choices (blue,green,red) and this exception is handled in the robot class inside model package

<h2> 3- NeartoCellException:</h2> 

 This exception is thrown incase the robot is too near to cell before moving, it is used to detect the closed corner


<strong><h1> 4-Solvers Package </h1></strong>

This package includes the main algorithms required to search for the required colorful walls, it includes an interface , and a wallFollwer:


<h2> 1- WallFollower:</h2>

this is a simple deterministic algorithm required to look for the determined coloful color using the following method beside some other helping methods.

```java
WallFollower.solveMaze();
```

<h2> 2- IntelligentSolver:</h2>

this was considered to be anthor intelligent algorithm but, I faced some hardships to implement it on the hardware and even the simulation level.

```java
IntelligentSolver.solveMaze();
```
<strong><h1> 5-Main Package </h1></strong>

This is the main package that includes the run class which is reponsiable about inistantiating any solver class such as WallFollower 

<h2> 1- Run: </h2>.
This is the main class of this software, in which we run the whole program 

<strong> <h1> 6-tools package </h1></strong>

This package include some important building blocks required to build the follower algorithm such as wall follower.

<h2> 1- Direction: </h2>

Enum Direction provides 2 options for the robot to take during turning operation either CW or CCW


<h2> 2- Follower: </h2>

This abstract class is the backbone of follower algorithms as they provide almost every required tool to enable the robot to go through the maze roads 


<h2> 3- Solvable: </h2>

interface Solvable is required to give the followers'algorithms solving features, in other words , each algorithm will implement solve method which will be a common feature between all of them


<h2> 4- Terminable: </h2>

interface Terminable , is requried to terminate the followers algorithm once they exceed the time limit (5 minutes)


