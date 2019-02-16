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

public class Cmd_ArmJog extends Command {
  public Cmd_ArmJog() {
    requires(Robot.s_arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double signvalue = Robot.s_arm.dartMotor0Position();
    float sign = Math.signum((float)signvalue);

    if((int)Robot.m_oi.getLeftField().getRawAxis(0) < 0){
      if( Robot.s_arm.isArmHome() == false){
        Robot.s_arm.armStop();
      }
      else{
        Robot.s_arm.dartDriveGoDown();

      }
      // Robot.s_arm.dartDriveGoDown();

    }
    else if((int)Robot.m_oi.getLeftField().getRawAxis(0) > 0){
      Robot.s_arm.dartDriveGoUp();
    }

    else{
      Robot.s_arm.armStop();
    }
    SmartDashboard.putNumber("ArmLiveTicks", Robot.s_arm.dartMotor0Position());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
