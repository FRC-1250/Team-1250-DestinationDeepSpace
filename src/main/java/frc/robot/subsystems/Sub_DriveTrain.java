/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Cmd_ManualDrive;


public class Sub_DriveTrain extends Subsystem {

  //Spark Max motor controllers for drive train

  CANSparkMax fRightMotor = new CANSparkMax(RobotMap.DRV_RIGHT_FRONT, MotorType.kBrushless);
  CANSparkMax mRightMotor = new CANSparkMax(RobotMap.DRV_RIGHT_MID, MotorType.kBrushless);
  CANSparkMax bRightMotor = new CANSparkMax(RobotMap.DRV_RIGHT_BACK, MotorType.kBrushless);
  CANSparkMax fLeftMotor = new CANSparkMax(RobotMap.DRV_LEFT_FRONT, MotorType.kBrushless);
  CANSparkMax mLeftMotor = new CANSparkMax(RobotMap.DRV_LEFT_MID, MotorType.kBrushless);
  CANSparkMax bLeftMotor = new CANSparkMax(RobotMap.DRV_LEFT_BACK, MotorType.kBrushless);

  //Driving motor comtroller groups for grouping drive sides without using Masters and Followers

  private SpeedController gRightSide = new SpeedControllerGroup(fRightMotor, mRightMotor, bRightMotor);
  private SpeedController gLeftSide = new SpeedControllerGroup(fLeftMotor, mRightMotor, bLeftMotor);

  //Drive group sides

  private DifferentialDrive diffDriveGroup = new DifferentialDrive(gLeftSide, gRightSide);

  //Limelight math variables

  private double CUBE_AREA_SETPOINT= 0;

  //Constants for Closed Loop Feedback

	public static double accumError = 0;
	private final double AUTO_TURN_RATE = 0.3;
	private final double KP_SIMPLE_STRAIT = 0.01;
	private final double KP_SIMPLE = 0.05;
  private final double KI_SIMPLE = 0.03;
  
  ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kMXP);

  public int driveSetpoint = 0;
	private final double DRIVE_TICKS = 354.9;

public Sub_DriveTrain(){

  //Setting Linear Voltage Ramps for Drive Motors  TO DO - Check if values are high/low enough for robot
  //RightSide Ramps
  fRightMotor.setRampRate(.1);
  mRightMotor.setRampRate(.1);
  bRightMotor.setRampRate(.1);
  //LeftSide Ramps
  fLeftMotor.setRampRate(.1);
  mLeftMotor.setRampRate(.1);
  bLeftMotor.setRampRate(.1);

}


  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Cmd_ManualDrive());
  }

  //Methods for driving
  //The drive methods are overloaded btw

  public void drive(double left, double right){
    diffDriveGroup.tankDrive(left, right);
  }

  public void drive(Joystick joy){
    drive(joy.getY(), joy.getThrottle());
  }

  public void driveArcade(Joystick joy) {
		diffDriveGroup.arcadeDrive(-joy.getThrottle(),joy.getZ());
	}

  public double leftVelocity(){
    return fLeftMotor.getEncoder().getVelocity();
  }

  //Encoder feedback from the drivetrain
  //Velocity from each side

  public double rightVelocity(){
    return fRightMotor.getEncoder().getVelocity();
  }

  public double leftPosition(){
    return fLeftMotor.getEncoder().getPosition();
  }

  public double rightPostion(){
    return fRightMotor.getEncoder().getPosition();
  }

  //Gyro Feedback and control

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  public void resetGyro() {
    gyro.reset();
  }

  //Auton Methods
  //Checks for if robot is done driving to position

  public boolean isDoneDriving() {
    return ((Math.abs(this.rightPostion() - driveSetpoint) >= 0));
  }
  
  //Checks for if robot is done turning

  public boolean isDoneTurning(double angle) {
    return (Math.abs(angle - this.getGyroAngle()) < 2);
  }

  //Method for driving to position in auton

  public void driveToPos( double upperSpeed, double lowerSpeed) {
    	
    double offset = getGainP(0,this.getGyroAngle(),KP_SIMPLE_STRAIT);
    
    double sign = Math.signum(driveSetpoint);
    
    diffDriveGroup.arcadeDrive(linearRamp(upperSpeed,lowerSpeed) * sign, 0 + offset);
    
  }

  //Method for a linear ramping to a lower speed for auton driving
  //This increases accuracy for auton driving
  //uppperSpped is the fastest the robot will drive and lowerSpeed is the slowest the robot will drive
  //The speed is measured from 0 to 1, so 0.5 will be 50% motor output and 1 will be 100%

  private double linearRamp(double upperSpeed, double lowerSpeed) {
    double diff = (driveSetpoint - (double)Math.abs(rightPostion()));
    double corrected = .05 * diff;
    double upperBound = Math.min(upperSpeed , corrected);
    double lowerBound = Math.max(lowerSpeed , upperBound);
    
    SmartDashboard.putNumber("correctedoutput", corrected);
    return lowerBound;
  }

  //Methods for limelight tracking

  public void setpointCube(double areaSetpoint) {
    CUBE_AREA_SETPOINT = areaSetpoint;
  }

  //Automatic allignment and driving method

  public void trackCube(double xOffset, double areaOffset) {
  
  double xDiff = 0 - xOffset *-1;
  double aDiff = 5 - areaOffset;
  double xCorrect = 0.05 * xDiff;
  double aCorrect = 0.4 * aDiff;
  
  diffDriveGroup.arcadeDrive(aCorrect, xCorrect);
}

//Automatic allighnment but accepts the left joystick Y value as speed
//TO DO TEST THIS ONE :)

public void trackCubeManualSpeed(double xOffset, Joystick joy) {
  
  double xDiff = 0 - xOffset *-1;
  double xCorrect = 0.05 * xDiff;
  
  diffDriveGroup.arcadeDrive(joy.getY(), xCorrect);
}

//Assorted PID math for auto driving
  
  private double getGainP(double setpoint, double current, double kP) {
    	
    double error = setpoint - current;  		
    return KP_SIMPLE * error;
  }
  
  private double getGainPI(double setpoint, double current,double kP, double kI) {
    
    double error = setpoint - current; 
    double p = KP_SIMPLE * error;
    accumError = accumError + error;
    double i = KI_SIMPLE * error;
    return p + i;
  }


}
