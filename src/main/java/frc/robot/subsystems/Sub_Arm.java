/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Sub_Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX dartMotor0 = new WPI_TalonSRX(RobotMap.ARM_DART0);
  WPI_TalonSRX dartMotor1 = new WPI_TalonSRX(RobotMap.ARM_DART1);
  DigitalInput armHomeSensor = new DigitalInput(RobotMap.ARM_HOME);

  private SpeedController gDartDrive = new SpeedControllerGroup(dartMotor0, dartMotor1);

  public static double accumError = 0;
	private final double KP_SIMPLE = 0.05;
  private final double KI_SIMPLE = 0.03;

  double armSetpoint0 = 0;

  public Sub_Arm(){

    dartMotor0.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    dartMotor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
  }


  // TODO: Figure out the math of this
  private final double DRIVE_TICKS = 0;

  public double dartMotor0Position(){
    return dartMotor0.getSelectedSensorPosition();
  }
  public double dartMotor1Position(){
    return dartMotor1.getSelectedSensorPosition();
  }
public void resetArmPos(){
  dartMotor0.setSelectedSensorPosition(0);
  dartMotor1.setSelectedSensorPosition(0);

}

  public boolean isArmHome(){
    return armHomeSensor.get();
  }
  

  public void setArmPos(int distance){
    armSetpoint0 = DRIVE_TICKS * (distance);
  }

  public boolean isDoneDriving() {
        
    double currVal = this.dartMotor0Position();
    double distToPos = currVal - armSetpoint0;
    SmartDashboard.putNumber("DistToPosArm", distToPos);
    return (distToPos >= 0);
}

public boolean isDoneDrivingBack() {
    
    double currVal = this.dartMotor0Position();
    double distToPos = currVal - armSetpoint0;
    SmartDashboard.putNumber("DistToPosArm", distToPos);
    return (distToPos <= 0);
}

public void moveToPos( double upperSpeed, double lowerSpeed) {

  gDartDrive.set(linearRamp(upperSpeed,lowerSpeed));
  
}

private double linearRamp(double upperSpeed, double lowerSpeed) {
  double diff = (armSetpoint0 - (double)Math.abs(dartMotor0Position()));
  double corrected = .05 * diff;
  double upperBound = Math.min(upperSpeed , corrected);
  double lowerBound = Math.max(lowerSpeed , upperBound);
  
  SmartDashboard.putNumber("correctedoutputArm", corrected);
  return lowerBound;
}




  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
