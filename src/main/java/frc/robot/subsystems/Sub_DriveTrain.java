/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
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

  //The drive methods are overloaded btw

  public void drive(double left, double right){
    diffDriveGroup.tankDrive(left, right);
  }

  public void drive(Joystick joy){
    drive(joy.getY(), joy.getThrottle());
  }


}
