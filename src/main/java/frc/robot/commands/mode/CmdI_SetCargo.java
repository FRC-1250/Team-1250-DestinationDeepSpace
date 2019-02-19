/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.mode;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class CmdI_SetCargo extends InstantCommand {

  public CmdI_SetCargo() {
    super();
  }

  @Override
  protected void initialize() {
    Robot.m_oi.setAllowedCargoButtons();
    Robot.mode = "cargo";
  }

}
