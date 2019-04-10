/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Sub_Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

Solenoid climbSolenoid = new Solenoid(RobotMap.CLIMB_SOLENOID);

  @Override
  public void initDefaultCommand() {
    
  }

  public void climberDeploy(){
    climbSolenoid.set(true);
  }

  public void climberRetract(){
    climbSolenoid.set(false);
  }

  
}
