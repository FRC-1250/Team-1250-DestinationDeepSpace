/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Cmd_DoNothing;
import frc.robot.commands.drive.Cmd_AutoDrive;
import frc.robot.commands.drive.Cmd_AutoTurn;

public class CmdG_BoringClimb extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CmdG_BoringClimb() {
    addSequential(new CmdI_ClimberDeploy());
    addSequential(new Cmd_DoNothing(2));
    addSequential(new Cmd_AutoDrive(-10, 0.3, 0.3));
    addSequential(new CmdI_ClimberRetract());
    addSequential(new Cmd_DoNothing(.5));
    addSequential(new Cmd_AutoDrive(-125, 1, 1));


  }
}
