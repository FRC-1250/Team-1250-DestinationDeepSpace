/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Cmd_armIntakeSpit extends TimedCommand {
  /**
   * Add your docs here.
   */
  public Cmd_armIntakeSpit(double timeout) {
    super(timeout);
      requires(Robot.s_collector);
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
    Robot.s_collector.armIntakeSpit();
  }

  // Called once after timeout
  @Override
  protected void end() {
    Robot.s_collector.armIntakeStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.s_collector.armIntakeStop();
  }
}
