/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; 



public class Sub_Collector extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Solenoid solenoidHatch = new Solenoid(RobotMap.COL_SOL_HAT);
  Solenoid solenoidCollect = new Solenoid(RobotMap.COL_SOL_COL);
  DigitalInput sensorHatch = new DigitalInput(RobotMap.COL_SENSE_HATCH);
  DigitalInput sensorBall = new DigitalInput(RobotMap.COL_SENSE_BALL);
  WPI_VictorSPX dropMotor0 = new WPI_VictorSPX(RobotMap.COL_DROPMOTOR_0);
  WPI_VictorSPX dropMotor1 = new WPI_VictorSPX(RobotMap.COL_DROPMOTOR_1);
  WPI_VictorSPX armController0 = new WPI_VictorSPX(RobotMap.COL_ARM0);
  WPI_VictorSPX armController1 = new WPI_VictorSPX(RobotMap.COL_ARM1);


  // Motor groups for collector

  private SpeedController gDropMotors = new SpeedControllerGroup(dropMotor0, dropMotor1);
  private SpeedController gArmControllers = new SpeedControllerGroup(armController0, armController1);

  public Sub_Collector() {
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  // Methods for collector, collection speed, and throw speed
  public void collectorIntake() {
    gArmControllers.set(1);
  }
  public void collectorThrow() {
    gArmControllers.set(-1);
  }
  // Stop collector
  public void collectorStop() {
    gArmControllers.set(0);
  }
  // Half the speed of the cargo throw
  public void collectorThrowSlow() {
    gArmControllers.set(-0.5);
  }
  // Sets speed of collector
  public void collectorSetSpeed(double speed) {
    gArmControllers.set(speed);
  }
  // Extends hatch cylinders
  public void solenoidExtendHatch() {
    solenoidHatch.set(true);
  }
  // Retracts hatch cylinders
  public void solenoidRetractHatch() {
    solenoidHatch.set(false);
  }
  // Extends collector cylinders
  public void solenoidExtendCollector() {
    solenoidCollect.set(true);
  }
  // Retracts collector cylinders
  public void solenoidRetractCollector() {
    solenoidCollect.set(false);
  }
  // Returns if hatch sensor is activated
  public boolean isHatchSensor() {
    return sensorHatch.get();
  }
  // Returns if ball sensor is activated
  public boolean isBallSensor() {
    return sensorBall.get();
  }
  // Sets speed of drop motors
  public void dropMotorSetSpeed(double speed) {
    gDropMotors.set(speed);
  }
}
