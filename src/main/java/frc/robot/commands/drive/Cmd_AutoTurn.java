/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Cmd_AutoTurn extends Command {

  double angle = 0;
  double upperSpeed = 0;
  double lowerSpeed = 0;

  public Cmd_AutoTurn(double angle, double upperSpeed, double lowerSpeed) {
    requires(Robot.s_drivetrain);
    this.angle = angle;
    this.upperSpeed = upperSpeed;
    this.lowerSpeed = lowerSpeed;
    Robot.s_drivetrain.resetGyro();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.s_drivetrain.resetGyro();
    setTimeout(5);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.s_drivetrain.turn(angle, upperSpeed, lowerSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.s_drivetrain.isDoneTurning(angle) || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.s_drivetrain.driveStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.s_drivetrain.driveStop();

  }
}
