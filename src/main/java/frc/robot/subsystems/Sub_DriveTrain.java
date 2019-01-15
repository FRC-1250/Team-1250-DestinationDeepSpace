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



  public boolean isDoneDriving() {
    return ((Math.abs(this.getRightSideSensorPosInTicks()) - driveSetpoint) >= 0);
  }
    
  public boolean isDoneTurning(double angle) {
    return (Math.abs(angle - this.getGyroAngle()) < 2);
  }

  public void driveToPos( double upperSpeed, double lowerSpeed) {
    	
    double offset = getGainP(0,this.getGyroAngle(),KP_SIMPLE_STRAIT);
    
    double sign = Math.signum(driveSetpoint);
    
    diffDriveGroup.arcadeDrive(linearRamp(upperSpeed,lowerSpeed) * sign, 0 + offset);
    
  }
  private double linearRamp( double upperSpeed, double lowerSpeed) {
    double diff = (driveSetpoint - (double)Math.abs(getRightSideSensorPosInTicks()));
    double corrected = .05 * diff;
    double upperBound = Math.min(upperSpeed , corrected);
    double lowerBound = Math.max(lowerSpeed , upperBound);
    
    SmartDashboard.putNumber("correctedoutput", corrected);
    return lowerBound;
  }


}
