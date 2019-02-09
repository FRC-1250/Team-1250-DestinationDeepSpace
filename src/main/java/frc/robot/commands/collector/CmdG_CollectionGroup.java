/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CmdG_CollectionGroup extends CommandGroup {

  //Runs all parts of robot for collection

  public CmdG_CollectionGroup() {
    addParallel(new Cmd_CollectorDropMotors());
    addParallel(new Cmd_CollectorForwardExtend());
    addSequential(new Cmd_CollectorInput());
  }
}
