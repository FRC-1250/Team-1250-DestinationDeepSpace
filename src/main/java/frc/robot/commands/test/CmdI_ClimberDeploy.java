/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class CmdI_ClimberDeploy extends InstantCommand {
  /**
   * Add your docs here.
   */
  public CmdI_ClimberDeploy() {
    super();
    requires(Robot.s_climber);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.s_climber.climberDeploy();
  }

}