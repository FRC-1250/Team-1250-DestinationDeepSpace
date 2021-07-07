/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Cmd_DoNothing;
import frc.robot.commands.collector.CmdI_CollectorHatchFullPlace;
import frc.robot.commands.collector.CmdI_CollectorHatchTongueExtend;
import frc.robot.commands.drive.Cmd_AutoDrive;
import frc.robot.commands.drive.Cmd_AutoTurn;
import frc.robot.commands.drive.Cmd_TrackingDrive;

public class CmdG_AutoRightHatchRightHab2 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CmdG_AutoRightHatchRightHab2() {
    // addSequential(new Cmd_AutoDrive(70, 0.3, 0.6));
    // addSequential(new Cmd_AutoTurn(-10, 0.3, 0.6));
    // addSequential(new Cmd_DoNothing(1));
    addSequential(new Cmd_AutoTurn(-45, 0.1, 0.3));
    addSequential(new Cmd_AutoDrive(35, 0.2, 0.4));
    addSequential(new Cmd_AutoTurn(45, 0.1, 0.3));
    addSequential(new Cmd_TrackingDrive(45, 0.4, 0.2));
    addSequential(new Cmd_AutoTurn(-2, 0.3, 0.6));
    addSequential(new Cmd_AutoDrive(10, 0.2, 0.4));
    // addSequential(new CmdI_CollectorHatchFullPlace());
    // addSequential(new Cmd_DoNothing(1));
    // addSequential(new Cmd_AutoDrive(-30, 0.3, 0.6));
    // addSequential(new Cmd_AutoTurn(120, 0.3, 0.6));
  }
}
