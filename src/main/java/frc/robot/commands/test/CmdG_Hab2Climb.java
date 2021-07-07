/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.drive.Cmd_AutoDrive;

public class CmdG_Hab2Climb extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CmdG_Hab2Climb() {
    addSequential(new Cmd_AutoDrive(-35, 0.2, 0.4));
    addSequential(new Cmd_AutoYeet(90, 1, 1));

  }
}
