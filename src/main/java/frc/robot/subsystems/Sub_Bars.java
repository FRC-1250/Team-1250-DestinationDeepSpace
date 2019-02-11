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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Sub_Bars extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  CANSparkMax barMotorLeft = new CANSparkMax(RobotMap.BAR_MOTORLEFT, MotorType.kBrushless);
  CANSparkMax barMotorRight = new CANSparkMax(RobotMap.BAR_MOTORRIGHT, MotorType.kBrushless);
  DigitalInput homeRightSensor = new DigitalInput(RobotMap.BAR_HOMERIGHT);
  DigitalInput homeLeftSensor = new DigitalInput(RobotMap.BAR_HOMELEFT);
  DigitalInput hatchRightSensor = new DigitalInput(RobotMap.BAR_HATCHRIGHT);
  DigitalInput hatchLeftSensor = new DigitalInput(RobotMap.BAR_HATCHLEFT);

public Sub_Bars(){
  barMotorLeft.setInverted(true);
  barMotorRight.setInverted(true);
  barMotorLeft.setIdleMode(IdleMode.kBrake);
  barMotorRight.setIdleMode(IdleMode.kBrake);
}

  private SpeedController gBarMotors = new SpeedControllerGroup(barMotorLeft, barMotorRight);

  public boolean isBarHomeLeft() {
    return homeLeftSensor.get();
  }

  public boolean isBarHomeRight() {
    return homeRightSensor.get();
  }

  public boolean isHatchInLeft(){
    return hatchLeftSensor.get();
  }

  public boolean isHatchInRight(){
    return hatchRightSensor.get();
  }

  public void barsManualSpeed(double speed){
    gBarMotors.set(speed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}


