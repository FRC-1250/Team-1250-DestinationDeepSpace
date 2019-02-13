/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Cmd_CollectorCollectCargoDropMotors extends Command {
  public Cmd_CollectorCollectCargoDropMotors() {
    requires(Robot.s_collector);
  }

  @Override
  protected void initialize() {
    Robot.s_collector.extendDropMotors();
    Robot.s_collector.armIntakeCollect();
    Robot.s_collector.dropMotorSetSpeed(1);
  }

  @Override
  protected void execute() {
    //No need to run continuously 
  }

  @Override
  protected boolean isFinished() {
    return(Robot.s_collector.isBallSensor() == false);
  }

  @Override
  protected void end() {
    Robot.s_collector.dropMotorSetSpeed(0);
    Robot.s_collector.armIntakeStop();
    Robot.s_collector.retractDropMotors();
  }

  @Override
  protected void interrupted() {
    //Interrupt same as end
    end();
  }
}
