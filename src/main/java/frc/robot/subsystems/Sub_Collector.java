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


public class Sub_Collector extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Solenoid solenoidHatch = new Solenoid(RobotMap.COL_SOL_HATCH);
  Solenoid solenoidDropMotors = new Solenoid(RobotMap.COL_SOL_DROPINTAKEMOTORS);
  Solenoid solenoidPokeEyes = new Solenoid(RobotMap.COL_SOL_POKEEYES);
  DigitalInput sensorHatch = new DigitalInput(RobotMap.COL_SENSE_HATCH);
  DigitalInput sensorBall = new DigitalInput(RobotMap.COL_SENSE_BALL);
  WPI_VictorSPX dropIntakeMotor0 = new WPI_VictorSPX(RobotMap.COL_DROPINTAKEMOTOR_0);
  WPI_VictorSPX dropIntakeMotor1 = new WPI_VictorSPX(RobotMap.COL_DROPINTAKEMOTOR_1);
  WPI_VictorSPX armIntake0 = new WPI_VictorSPX(RobotMap.COL_ARMINTAKEMOTOR_0);
  WPI_VictorSPX armIntake1 = new WPI_VictorSPX(RobotMap.COL_ARMINTAKEMOTOR_1);

  // Motor groups for collector
  private SpeedController gDropIntakeMotors = new SpeedControllerGroup(dropIntakeMotor0, dropIntakeMotor1);
  private SpeedController gArmIntakeMotors = new SpeedControllerGroup(armIntake0, armIntake1);

  public Sub_Collector() {
    armIntake1.setInverted(true);
    dropIntakeMotor1.setInverted(true);
    dropIntakeMotor0.setInverted(false);

  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  // Methods for collector, collection speed, and throw speed
  public void armIntakeCollect() {
    gArmIntakeMotors.set(1);
  }
  public void armIntakeSpit() {
    gArmIntakeMotors.set(-1);
  }
  // Stop collector
  public void armIntakeStop() {
    gArmIntakeMotors.set(0);
  }
  // Half the speed of the cargo throw
  public void armIntakeSlowSpit() {
    gArmIntakeMotors.set(-0.5);
  }
  // Sets speed of collector
  public void setArmIntakeSpeed(double speed) {
    gArmIntakeMotors.set(speed);

  }
  // Extends hatch cylinders
  public void extendHatchTongue() {
    solenoidHatch.set(true);
  }
  // Retracts hatch cylinders
  public void retractHatchTongue() {
    solenoidHatch.set(false);
  }
  // Extends collector cylinders
  public void extendDropMotors() {
    solenoidDropMotors.set(true);
  }
  // Retracts collector cylinders
  public void retractDropMotors() {
    solenoidDropMotors.set(false);
  }

  public void extendPokeEyes(){
    solenoidPokeEyes.set(true);
  }

  public void retractPokeEyes(){
    solenoidPokeEyes.set(false);
  }

  // Returns if hatch sensor is activated
  public boolean isBallSensorBackup() {
    return sensorHatch.get();
  }
  // Returns if ball sensor is activated
  public boolean isBallSensor() {
    return sensorBall.get();
  }
  // Sets speed of drop motors
  public void dropMotorSetSpeed(double speed) {
    gDropIntakeMotors.set(speed);
  }
}
