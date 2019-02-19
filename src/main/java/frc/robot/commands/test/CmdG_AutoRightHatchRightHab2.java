/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Cmd_DoNothing;
import frc.robot.commands.collector.CmdI_CollectorHatchTongueExtend;
import frc.robot.commands.drive.Cmd_AutoDrive;
import frc.robot.commands.drive.Cmd_AutoTurn;
import frc.robot.commands.drive.Cmd_TrackingDrive;

public class CmdG_AutoRightHatchRightHab2 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CmdG_AutoRightHatchRightHab2() {
    addSequential(new Cmd_AutoDrive(-60, 0.3, 0.6));
    addSequential(new Cmd_AutoTurn(160, 0.2, 0.4));
    addSequential(new Cmd_DoNothing(1));
    addSequential(new Cmd_TrackingDrive(60, 0.6, 0.3));
    addSequential(new CmdI_CollectorHatchTongueExtend());
    addSequential(new Cmd_DoNothing(1));
    addSequential(new Cmd_AutoDrive(-30, 0.3, 0.6));
    addSequential(new Cmd_AutoTurn(120, 0.3, 0.6));
  }
}
