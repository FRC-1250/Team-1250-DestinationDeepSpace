/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CmdG_CollectorFullCollectWithTiming extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CmdG_CollectorFullCollectWithTiming() {
   addSequential(new Cmd_CollectorCollectCargoDropMotors());
   addSequential(new CmdT_CollectorArmIntakeTimed(0.5));
  }
}
