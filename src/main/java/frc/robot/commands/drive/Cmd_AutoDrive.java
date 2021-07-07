/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Cmd_AutoDrive extends Command {

  int distance = 0;
  double upperSpeed;
  double lowerSpeed;
  float sign;

  public Cmd_AutoDrive(int distance, double upperSpeed, double lowerSpeed) {
    requires(Robot.s_drivetrain);
    this.distance = distance;
    this.upperSpeed = upperSpeed;
    this.lowerSpeed = lowerSpeed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.s_drivetrain.drivePosReset();
    Robot.s_drivetrain.resetGyro();
    Robot.s_drivetrain.setSetpointPos(distance);
    setTimeout(15);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.s_drivetrain.driveToPos(upperSpeed, lowerSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    sign = Math.signum(distance);
        
        if (sign == 1){
            return Robot.s_drivetrain.isDoneDriving() || isTimedOut();
        }
        if (sign == -1){
            return Robot.s_drivetrain.isDoneDrivingBack() || isTimedOut();
        }
        else{
            return false;
        }
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
