/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class CmdI_CollectorHatchTongueRetract extends InstantCommand {
  
  public CmdI_CollectorHatchTongueRetract() {
    super();
    requires(Robot.s_collector);
  }

  @Override
  protected void initialize() {
    Robot.s_collector.retractHatchTongue();
    Robot.s_collector.retractPokeEyes();
  }

}
