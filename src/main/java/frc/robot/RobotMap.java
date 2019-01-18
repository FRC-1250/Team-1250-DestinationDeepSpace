/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  //Drive motor controller CAN IDs
  //Right Drive IDs
  public static final int DRV_RIGHT_FRONT = 10;
  public static final int DRV_RIGHT_MID = 11;
  public static final int DRV_RIGHT_BACK = 12;
  //Left Drive IDs
  public static final int DRV_LEFT_FRONT = 13;
  public static final int DRV_LEFT_MID = 14;
  public static final int DRV_LEFT_BACK = 15;
// Collector Motor IDs
  public static final int COL_LEFT = 16;
  public static final int COL_RIGHT = 17;
// Solenoid PCM Channel
  public static final int COL_SOL_HAT = 0;
  public static final int COL_SOL_COL = 1;
// Collector Sensor IDs
  public static final int COL_SENSE_HATCH = 0;
  public static final int COL_SENSE_BALL = 1;
// Drop Motor Victors IDs
  public static final int COL_DROPMOTOR_0 = 18;
  public static final int COL_DROPMOTOR_1 = 19;

}
