/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;


public class Cmd_ArmCargoHigh extends Command {
  float sign;
  int distance = 0;
  double currentPos = Robot.s_arm.dartMotor0Position();


  public Cmd_ArmCargoHigh() {
    requires(Robot.s_arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
    setTimeout(5);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putString("Trigger", "CargoHigh");
    Robot.s_arm.setArmCargoHigh();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    return(Robot.s_arm.dartMotor0Position() ==  Robot.s_arm.highCargoPos || isTimedOut());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.s_arm.armStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.s_arm.armStop();
  }
}
