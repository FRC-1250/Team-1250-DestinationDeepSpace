/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry; 
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

/**
 * Add your docs here.
 */
public class Sub_Limelight extends Subsystem {
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getCubeX() {
    NetworkTableEntry tx = table.getEntry("tx");
    return  tx.getDouble(0); 
  }
  
  public double getCubeY() {
    NetworkTableEntry ty = table.getEntry("ty");
    return ty.getDouble(0);
  }
  
  public double getCubeArea() {
    NetworkTableEntry ta = table.getEntry("ta");
    return ta.getDouble(0);
  }
}
