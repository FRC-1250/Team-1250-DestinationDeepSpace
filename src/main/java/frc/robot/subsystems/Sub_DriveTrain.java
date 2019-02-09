/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.drive.Cmd_ManualDrive;


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
  private SpeedController gLeftSide = new SpeedControllerGroup(fLeftMotor, mLeftMotor, bLeftMotor);

  //Drive group sides

  private DifferentialDrive diffDriveGroup = new DifferentialDrive(gLeftSide, gRightSide);




  //Constants for Closed Loop Feedback

	public static double accumError = 0;
	private final double AUTO_TURN_RATE = 0.3;
	private final double KP_SIMPLE_STRAIT = 0.01;
	private final double KP_SIMPLE = 0.05;
  private final double KI_SIMPLE = 0.03;
  
  AnalogGyro gyro = new AnalogGyro(RobotMap.GYRO);

  public double driveSetpoint = 0;
	private final double DRIVE_TICKS = 0.448286;

public Sub_DriveTrain(){

  //Setting Linear Voltage Ramps for Drive Motors  TO DO - Check if values are high/low enough for robot
  //RightSide Ramps
  fRightMotor.setRampRate(0.8);
  mRightMotor.setRampRate(0.8);
  bRightMotor.setRampRate(0.8);
  //LeftSide Ramps
  fLeftMotor.setRampRate(0.8);
  mLeftMotor.setRampRate(0.8);
  bLeftMotor.setRampRate(0.8);
  //Right Idlemode
  fRightMotor.setIdleMode(IdleMode.kCoast);
  mRightMotor.setIdleMode(IdleMode.kCoast);
  bRightMotor.setIdleMode(IdleMode.kCoast);
  //Left Idlemode
  fLeftMotor.setIdleMode(IdleMode.kCoast);
  mLeftMotor.setIdleMode(IdleMode.kCoast);
  bLeftMotor.setIdleMode(IdleMode.kCoast);
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
    drive(-joy.getY(), -joy.getThrottle());
  }

  public void driveArcade(Joystick joy) {
		diffDriveGroup.arcadeDrive(-joy.getThrottle(),joy.getZ());
	}

  //Encoder feedback from the drivetrain
  //Velocity from each side

  public double leftVelocity(){
    return fLeftMotor.getEncoder().getVelocity();
  }

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
  //Gets angle form gryo

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  //Sets Current gyro position to 0 degrees

  public void resetGyro() {
    gyro.reset();
  }

  //Accepts the distance that you want the robot to go during auto motions
  //Converts the distance in inches to DRIVE_TICKS
  //Factors in the current position of the encoders on the neo so there os no need for encoder resets anymore

  public void setSetpointPos(int distance){
    driveSetpoint = (DRIVE_TICKS * distance) + leftPosition();
  }

  //Auton Methods
  //Checks for if robot is done driving to position

  public boolean isDoneDriving() {    
    double currVal = this.leftPosition();
    double distToPos = currVal - driveSetpoint;
    SmartDashboard.putNumber("DistToPos", distToPos);
    return (distToPos >= 0);
}

public boolean isDoneDrivingBack() {   
    double currVal = this.leftPosition();
    double distToPos = currVal - driveSetpoint;
    SmartDashboard.putNumber("DistToPosBack", distToPos);
    return (distToPos <= 0);
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

  //Method for auto turning, accepts an angle and an upper and lower turning speed for ramping

  public void turn (double angle, double upperSpeed, double lowerSpeed) {
    double corrected;
    double rotation = angle - getGyroAngle();
    double sign = Math.signum(rotation);      
    corrected = 0.05 * rotation;
            
      if (sign > 0){
              corrected = Math.min(upperSpeed * sign, corrected);
              corrected = Math.max(lowerSpeed * sign, corrected);
            }
                
      else {
              corrected = Math.max(upperSpeed * sign, corrected);
              corrected = Math.min(lowerSpeed * sign, corrected);                    
            }
    
            diffDriveGroup.arcadeDrive(0, corrected);
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
  //Automatic alighnment but accepts the left joystick Y value as speed
  //TO DO TEST THIS ONE :) // IT WORKS :)

public void trackCubeManualSpeed(double xOffset, double aJoy) {
  
  double xDiff = 0 - xOffset *-1;
  double xCorrect = 0.05 * xDiff;
  
  diffDriveGroup.arcadeDrive(aJoy, xCorrect);
}

  //Automatic alignment with auto distances

public void driveToPosTrack(double upperSpeed, double lowerSpeed, double xOffset) {

  double sign = Math.signum(driveSetpoint);
  
  double xDiff = xOffset;
  double xCorrect = xDiff;

  diffDriveGroup.arcadeDrive(linearRamp(upperSpeed,lowerSpeed) * sign, xCorrect);
  
}

  //Assorted PID math for auto driving
  
  private double getGainP(double setpoint, double current, double kP) {
    	
    double error = setpoint - current;  		
    return KP_SIMPLE * error;
  }

  //Methods to stop the drivetrain to smooth auto motions

  public void driveStop(){
    diffDriveGroup.arcadeDrive(0, 0);
  }

  public void pause(){
    drive(0,0);
  }


}

// ⠀⠀⠀⠀⣀⣤
// ⠀⠀⠀⠀⣿⠿⣶
// ⠀⠀⠀⠀⣿⣿⣀
// ⠀⠀⠀⣶⣶⣿⠿⠛⣶
// ⠤⣀⠛⣿⣿⣿⣿⣿⣿⣭⣿⣤
// ⠒⠀⠀⠀⠉⣿⣿⣿⣿⠀⠀⠉⣀
// ⠀⠤⣤⣤⣀⣿⣿⣿⣿⣀⠀⠀⣿
// ⠀⠀⠛⣿⣿⣿⣿⣿⣿⣿⣭⣶⠉
// ⠀⠀⠀⠤⣿⣿⣿⣿⣿⣿⣿
// ⠀⠀⠀⣭⣿⣿⣿⠀⣿⣿⣿
// ⠀⠀⠀⣉⣿⣿⠿⠀⠿⣿⣿
// ⠀⠀⠀⠀⣿⣿⠀⠀⠀⣿⣿⣤
// ⠀⠀⠀⣀⣿⣿⠀⠀⠀⣿⣿⣿
// ⠀⠀⠀⣿⣿⣿⠀⠀⠀⣿⣿⣿
// ⠀⠀⠀⣿⣿⠛⠀⠀⠀⠉⣿⣿
// ⠀⠀⠀⠉⣿⠀⠀⠀⠀⠀⠛⣿
// ⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⣿⣿
// ⠀⠀⠀⠀⣛⠀⠀⠀⠀⠀⠀⠛⠿⠿⠿
// ⠀⠀⠀⠛⠛


// ⠀⠀⠀⣀⣶⣀
// ⠀⠀⠀⠒⣛⣭
// ⠀⠀⠀⣀⠿⣿⣶
// ⠀⣤⣿⠤⣭⣿⣿
// ⣤⣿⣿⣿⠛⣿⣿⠀⣀
// ⠀⣀⠤⣿⣿⣶⣤⣒⣛
// ⠉⠀⣀⣿⣿⣿⣿⣭⠉
// ⠀⠀⣭⣿⣿⠿⠿⣿
// ⠀⣶⣿⣿⠛⠀⣿⣿
// ⣤⣿⣿⠉⠤⣿⣿⠿
// ⣿⣿⠛⠀⠿⣿⣿
// ⣿⣿⣤⠀⣿⣿⠿
// ⠀⣿⣿⣶⠀⣿⣿⣶
// ⠀⠀⠛⣿⠀⠿⣿⣿
// ⠀⠀⠀⣉⣿⠀⣿⣿
// ⠀⠶⣶⠿⠛⠀⠉⣿
// ⠀⠀⠀⠀⠀⠀⣀⣿
// ⠀⠀⠀⠀⠀⣶⣿⠿

// ⠀⠀⠀⠀⠀⠀⠀⠀⣤⣿⣿⠶⠀⠀⣀⣀
// ⠀⠀⠀⠀⠀⠀⣀⣀⣤⣤⣶⣿⣿⣿⣿⣿⣿
// ⠀⠀⣀⣶⣤⣤⠿⠶⠿⠿⠿⣿⣿⣿⣉⣿⣿
// ⠿⣉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⣤⣿⣿⣿⣀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣿⣿⣿⣿⣶⣤
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣿⣿⣿⣿⠿⣛⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⠛⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⣿⣿⠿⠀⣿⣿⣿⠛
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⠀⠀⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠿⠿⣿⠀⠀⣿⣶
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠛⠀⠀⣿⣿⣶
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣿⣿⠤
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⣿

// ⠀⠀⣀
// ⠀⠿⣿⣿⣀
// ⠀⠉⣿⣿⣀
// ⠀⠀⠛⣿⣭⣀⣀⣤
// ⠀⠀⣿⣿⣿⣿⣿⠛⠿⣶⣀
// ⠀⣿⣿⣿⣿⣿⣿⠀⠀⠀⣉⣶
// ⠀⠀⠉⣿⣿⣿⣿⣀⠀⠀⣿⠉
// ⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿
// ⠀⣀⣿⣿⣿⣿⣿⣿⣿⣿⠿
// ⠀⣿⣿⣿⠿⠉⣿⣿⣿⣿
// ⠀⣿⣿⠿⠀⠀⣿⣿⣿⣿
// ⣶⣿⣿⠀⠀⠀⠀⣿⣿⣿
// ⠛⣿⣿⣀⠀⠀⠀⣿⣿⣿⣿⣶⣀
// ⠀⣿⣿⠉⠀⠀⠀⠉⠉⠉⠛⠛⠿⣿⣶
// ⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣿
// ⠀⠀⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉
// ⣀⣶⣿⠛

// ⠀⠀⠀⠀⠀⠀⠀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⣿⣿⣿⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣤⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠉⣿⣿⣿⣶⣿⣿⣿⣶⣶⣤⣶⣶⠶⠛⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⣤⣿⠿⣿⣿⣿⣿⣿⠀⠀⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠛⣿⣤⣤⣀⣤⠿⠉⠀⠉⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠉⠉⠉⠉⠉⠀⠀⠀⠀⠉⣿⣿⣿⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣛⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⣶⣿⣿⠛⠿⣿⣿⣿⣶⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⣿⠛⠉⠀⠀⠀⠛⠿⣿⣿⣶⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⣿⣀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠿⣶⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠛⠿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣿⣿⠿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀

// ⠀⠀⠀⠀⠀⠀⣤⣶⣶
// ⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣀⣀
// ⠀⠀⠀⠀⠀⣀⣶⣿⣿⣿⣿⣿⣿
// ⣤⣶⣀⠿⠶⣿⣿⣿⠿⣿⣿⣿⣿
// ⠉⠿⣿⣿⠿⠛⠉⠀⣿⣿⣿⣿⣿
// ⠀⠀⠉⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣤⣤
// ⠀⠀⠀⠀⠀⠀⠀⣤⣶⣿⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⣀⣿⣿⣿⣿⣿⠿⣿⣿⣿⣿
// ⠀⠀⠀⠀⣀⣿⣿⣿⠿⠉⠀⠀⣿⣿⣿⣿
// ⠀⠀⠀⠀⣿⣿⠿⠉⠀⠀⠀⠀⠿⣿⣿⠛
// ⠀⠀⠀⠀⠛⣿⣿⣀⠀⠀⠀⠀⠀⣿⣿⣀
// ⠀⠀⠀⠀⠀⣿⣿⣿⠀⠀⠀⠀⠀⠿⣿⣿
// ⠀⠀⠀⠀⠀⠉⣿⣿⠀⠀⠀⠀⠀⠀⠉⣿
// ⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⣀⣿
// ⠀⠀⠀⠀⠀⠀⣀⣿⣿
// ⠀⠀⠀⠀⠤⣿⠿⠿⠿

// ⠀⠀⠀⠀⣀
// ⠀⠀⣶⣿⠿⠀⠀⠀⣀⠀⣤⣤
// ⠀⣶⣿⠀⠀⠀⠀⣿⣿⣿⠛⠛⠿⣤⣀
// ⣶⣿⣤⣤⣤⣤⣤⣿⣿⣿⣀⣤⣶⣭⣿⣶⣀
// ⠉⠉⠉⠛⠛⠿⣿⣿⣿⣿⣿⣿⣿⠛⠛⠿⠿
// ⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⠿
// ⠀⠀⠀⠀⠀⠀⠀⠿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⣭⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⣤⣿⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⠿
// ⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⠿
// ⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠉⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠉⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣿⠛⠿⣿⣤
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣿⠀⠀⠀⣿⣿⣤
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⣶⣿⠛⠉
// ⠀⠀⠀⠀⠀⠀⠀⠀⣤⣿⣿⠀⠀⠉
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉

// ⠀⠀⠀⠀⠀⠀⣶⣿⣶
// ⠀⠀⠀⣤⣤⣤⣿⣿⣿
// ⠀⠀⣶⣿⣿⣿⣿⣿⣿⣿⣶
// ⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿
// ⠀⠀⣿⣉⣿⣿⣿⣿⣉⠉⣿⣶
// ⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⠿⣿
// ⠀⣤⣿⣿⣿⣿⣿⣿⣿⠿⠀⣿⣶
// ⣤⣿⠿⣿⣿⣿⣿⣿⠿⠀⠀⣿⣿⣤
// ⠉⠉⠀⣿⣿⣿⣿⣿⠀⠀⠒⠛⠿⠿⠿
// ⠀⠀⠀⠉⣿⣿⣿⠀⠀⠀⠀⠀⠀⠉
// ⠀⠀⠀⣿⣿⣿⣿⣿⣶
// ⠀⠀⠀⠀⣿⠉⠿⣿⣿
// ⠀⠀⠀⠀⣿⣤⠀⠛⣿⣿
// ⠀⠀⠀⠀⣶⣿⠀⠀⠀⣿⣶
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣭⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⣤⣿⣿⠉

// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣶
// ⠀⠀⠀⠀⠀⣀⣀⠀⣶⣿⣿⠶
// ⣶⣿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣤⣤
// ⠀⠉⠶⣶⣀⣿⣿⣿⣿⣿⣿⣿⠿⣿⣤⣀
// ⠀⠀⠀⣿⣿⠿⠉⣿⣿⣿⣿⣭⠀⠶⠿⠿
// ⠀⠀⠛⠛⠿⠀⠀⣿⣿⣿⣉⠿⣿⠶
// ⠀⠀⠀⠀⠀⣤⣶⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⠒
// ⠀⠀⠀⠀⣀⣿⣿⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⣿⣿⣿⠛⣭⣭⠉
// ⠀⠀⠀⠀⠀⣿⣿⣭⣤⣿⠛
// ⠀⠀⠀⠀⠀⠛⠿⣿⣿⣿⣭
// ⠀⠀⠀⠀⠀⠀⠀⣿⣿⠉⠛⠿⣶⣤
// ⠀⠀⠀⠀⠀⠀⣀⣿⠀⠀⣶⣶⠿⠿⠿
// ⠀⠀⠀⠀⠀⠀⣿⠛
// ⠀⠀⠀⠀⠀⠀⣭⣶

// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣤
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿
// ⠀⠀⣶⠀⠀⣀⣤⣶⣤⣉⣿⣿⣤⣀
// ⠤⣤⣿⣤⣿⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣀
// ⠀⠛⠿⠀⠀⠀⠀⠉⣿⣿⣿⣿⣿⠉⠛⠿⣿⣤
// ⠀⠀⠀⠀⠀⠀⠀⠀⠿⣿⣿⣿⠛⠀⠀⠀⣶⠿
// ⠀⠀⠀⠀⠀⠀⠀⠀⣀⣿⣿⣿⣿⣤⠀⣿⠿
// ⠀⠀⠀⠀⠀⠀⠀⣶⣿⣿⣿⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠿⣿⣿⣿⣿⣿⠿⠉⠉
// ⠀⠀⠀⠀⠀⠀⠀⠉⣿⣿⣿⣿⠿
// ⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⠉
// ⠀⠀⠀⠀⠀⠀⠀⠀⣛⣿⣭⣶⣀
// ⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠉⠛⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⣿⣿
// ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣉⠀⣶⠿
// ⠀⠀⠀⠀⠀⠀⠀⠀⣶⣿⠿
// ⠀⠀⠀⠀⠀⠀⠀⠛⠿⠛

// ⠀⠀⠀⣶⣿⣶
// ⠀⠀⠀⣿⣿⣿⣀
// ⠀⣀⣿⣿⣿⣿⣿⣿
// ⣶⣿⠛⣭⣿⣿⣿⣿
// ⠛⠛⠛⣿⣿⣿⣿⠿
// ⠀⠀⠀⠀⣿⣿⣿
// ⠀⠀⣀⣭⣿⣿⣿⣿⣀
// ⠀⠤⣿⣿⣿⣿⣿⣿⠉
// ⠀⣿⣿⣿⣿⣿⣿⠉
// ⣿⣿⣿⣿⣿⣿
// ⣿⣿⣶⣿⣿
// ⠉⠛⣿⣿⣶⣤
// ⠀⠀⠉⠿⣿⣿⣤
// ⠀⠀⣀⣤⣿⣿⣿
// ⠀⠒⠿⠛⠉⠿⣿
// ⠀⠀⠀⠀⠀⣀⣿⣿
// ⠀⠀⠀⠀⣶⠿⠿⠛    
