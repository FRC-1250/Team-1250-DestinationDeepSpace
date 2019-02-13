/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.arm.*;
import frc.robot.commands.collector.*;
import frc.robot.commands.bars.*; 
import frc.robot.commands.test.*;
import frc.robot.commands.drive.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  //Controllers for driver (Gamepad) and for operator(Boards)
  Joystick Gamepad = new Joystick(0);
  Joystick BoardController = new Joystick(1);
  Joystick BoardLeftField = new Joystick(2);
  Joystick BoardRightField = new Joystick(3);

  //Buttons on Gamepad (Driver Controller)
	JoystickButton x = new JoystickButton(Gamepad, 1);
	JoystickButton a = new JoystickButton(Gamepad, 2);
	JoystickButton b = new JoystickButton(Gamepad, 3);
	JoystickButton y = new JoystickButton(Gamepad, 4);

  //lb slow cargo throw
  //rb fast cargo throw
	JoystickButton lb = new JoystickButton(Gamepad, 5);
  JoystickButton rb = new JoystickButton(Gamepad, 6);
  
  //Used in manual drive command
  //7 and 8 held, swich to arcade drive and track vision target while held
  //8 held, switch to arcade drive while held
	JoystickButton lt = new JoystickButton(Gamepad, 7);
  JoystickButton rt = new JoystickButton(Gamepad, 8);
  
  //Buttons on Board (OP Controller)
  JoystickButton defense = new JoystickButton(BoardController, 1);
  JoystickButton high = new JoystickButton(BoardController, 2);
  JoystickButton mid = new JoystickButton(BoardController, 3);
  JoystickButton low = new JoystickButton(BoardController, 4);
  JoystickButton hatch = new JoystickButton(BoardController, 5);
  JoystickButton cargo = new JoystickButton(BoardController, 6);
  JoystickButton home = new JoystickButton(BoardController, 7);
  
  JoystickButton leftRocketFar = new JoystickButton(BoardLeftField, 1);
  JoystickButton leftRocketMid = new JoystickButton(BoardLeftField, 2);
  JoystickButton leftRocketNear = new JoystickButton(BoardLeftField, 3);
  JoystickButton leftCargoShipFar = new JoystickButton(BoardLeftField, 4);
  JoystickButton leftCargoShipMid = new JoystickButton(BoardLeftField, 5);
  JoystickButton leftCargoShipNear = new JoystickButton(BoardLeftField, 6);
  JoystickButton leftCargoShipFront = new JoystickButton(BoardLeftField, 7);
  JoystickButton leftFeedingStation = new JoystickButton(BoardLeftField, 8);

  JoystickButton rightRocketFar = new JoystickButton(BoardRightField, 1);
  JoystickButton rightRocketMid = new JoystickButton(BoardRightField, 2);
  JoystickButton rightRocketNear = new JoystickButton(BoardRightField, 3);
  JoystickButton rightCargoShipFar = new JoystickButton(BoardRightField, 4);
  JoystickButton rightCargoShipMid = new JoystickButton(BoardRightField, 5);
  JoystickButton rightCargoShipNear = new JoystickButton(BoardRightField, 6);
  JoystickButton rightCargoShipFront = new JoystickButton(BoardRightField, 7);
  JoystickButton rightFeedingStation = new JoystickButton(BoardRightField, 8);

  public OI() {
    //Arm testing
    // y.whenActive(new Cmd_ArmManualUp());
    // a.whenActive(new Cmd_ArmManualDown());
    // b.whenActive(new Cmd_ArmManualStop());
    x.whenActive(new Cmd_CollectorHatchRemove(0.5));
    lb.whenPressed(new Cmd_CollectorInput());
    rb.whenActive(new CmdT_CollectorArmIntakeSpit(1));
  }

  public void setAllowedHomeButtons() {
 
  }

  public void setAllowedDefenseButtons() {

  }

  public void setAllowedCargoButtons() {
    //Auto positions cargo
    high.whenActive(new Cmd_ArmCargoHigh());
    mid.whenActive(new Cmd_ArmCargoMid());
    low.whenActive(new Cmd_ArmCargoLow());
    //Collector slow spit
    lb.whenActive(new CmdT_CollectorArmIntakeSlowSpit(2));
    //Collector fast spit
    rb.whenActive(new CmdT_CollectorArmIntakeSpit(1));
    
    //Intake on
    //a.whenPressed(new Cmd_CollectorDropMotors(0.5));
    //Intake off
    //b.whenPressed(new Cmd_CollectorDropMotors(0));
    
    //Jog up
    //Jog down 
  }

  public void setAllowedHatchButtons() {
    //Auto positions hatch
    high.whenActive(new Cmd_ArmHatchHigh());
    mid.whenActive(new Cmd_ArmHatchMid());
    low.whenActive(new Cmd_ArmHatchLow());

    //Hatch plunger
    //x.whenPressed(new Cmd_CollectorHatchRemove(0.5));
    
    //Jog up
    //Jog down
  }

  public Joystick getGamepad(){
    return Gamepad;
  }

  public Joystick getBoardController(){
    return BoardController;
  }

  public Joystick getLeftField(){
    return BoardLeftField;
  }

  public boolean getButtonState(int btn) {
    return Gamepad.getRawButton(btn);
  }

}
