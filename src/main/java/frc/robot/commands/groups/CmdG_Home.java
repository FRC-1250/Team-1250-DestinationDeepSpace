/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Cmd_DoNothing;
import frc.robot.commands.arm.*;
import frc.robot.commands.collector.*;

public class CmdG_Home extends CommandGroup {

  public CmdG_Home() {
    addParallel(new CmdI_CollectorHome());
    addParallel(new CmdI_CollectorHatchTongueRetract());
    addSequential(new Cmd_DoNothing(0.2));
    addSequential(new Cmd_ArmHome());
  }
}
