/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
<<<<<<< HEAD

public class Cmd_CollectorDropMotors extends Command {
  public Cmd_CollectorDropMotors() {
=======
import frc.robot.Robot;

public class Cmd_CollectorDropMotors extends Command {
  double speed;

public Cmd_CollectorDropMotors(double speed) {
    requires(Robot.s_collector);
    this.speed = speed;
>>>>>>> eseniya
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
<<<<<<< HEAD
=======
    Robot.s_collector.dropMotorSetSpeed(speed);
>>>>>>> eseniya
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
<<<<<<< HEAD
    return false;
  }
=======
    if (Robot.s_collector.isBallSensor()) {
      return true;
  } else {
    return false;
  }
  }
>>>>>>> eseniya

  // Called once after isFinished returns true
  @Override
  protected void end() {
<<<<<<< HEAD
=======
    Robot.s_collector.dropMotorSetSpeed(0);
>>>>>>> eseniya
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
<<<<<<< HEAD
=======
    Robot.s_collector.dropMotorSetSpeed(0);
>>>>>>> eseniya
  }
}
