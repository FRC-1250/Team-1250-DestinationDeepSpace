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
import frc.robot.commands.collector.CmdI_CollectorHatchTongueRetract;

public class Cmd_StartMatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Cmd_StartMatch() {
    addSequential(new CmdI_CollectorHatchTongueExtend());
    addSequential(new Cmd_DoNothing(.2));
    addSequential(new CmdI_CollectorHatchTongueRetract());
  }
}
