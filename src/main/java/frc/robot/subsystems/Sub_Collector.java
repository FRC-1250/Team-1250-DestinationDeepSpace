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
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; 



public class Sub_Collector extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // Spark Max motor controllers for collector

  CANSparkMax lCollect = new CANSparkMax(RobotMap.COL_LEFT, MotorType.kBrushless);
  CANSparkMax rCollect = new CANSparkMax(RobotMap.COL_RIGHT, MotorType.kBrushless);
  Solenoid solenoid = new Solenoid(RobotMap.COL_SOL);
  DigitalInput sensorHatch = new DigitalInput(RobotMap.COL_SENSE_HATCH);
  DigitalInput sensorBall = new DigitalInput(RobotMap.COL_SENSE_BALL);

  // Motor groups for collector

  private SpeedController gCollector = new SpeedControllerGroup(lCollect, rCollect);

  public Sub_Collector() {
    lCollect.setInverted(true);

  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  // Methods for collector, collection speed, and throw speed
  public void collectorIntake() {
    gCollector.set(1);
  }

  public void collectorThrow() {
    gCollector.set(-1);
  }
  // Stop collector:
  public void collectorStop() {
    gCollector.set(0);
  }
  // Half the speed of the cargo throw
  public void collectorThrowSlow() {
    gCollector.set(-0.5);
  }
  // Sets speed of collector
  public void collectorSetSpeed(double speed) {
    gCollector.set(speed);
  }
  // Extends cylinders
  public void solenoidExtend() {
    solenoid.set(true);
  }
  // Retracts cylinders
  public void solenoidRetract() {
    solenoid.set(false);
  }
  // Returns if hatch sensor is activated
  public boolean isHatchSensor() {
    return sensorHatch.get();
  }
  // Returns if ball sensor is activated
  public boolean isBallSensor() {
    return sensorBall.get();
  }

}
