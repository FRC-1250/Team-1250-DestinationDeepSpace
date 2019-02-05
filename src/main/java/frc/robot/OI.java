/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Cmd_ArmManualDown;
import frc.robot.commands.Cmd_ArmManualUp;
import frc.robot.commands.Cmd_ManualArmStop;
import frc.robot.commands.Cmd_ManualBars;
import frc.robot.commands.Cmd_TestDropRun;
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

	JoystickButton lb = new JoystickButton(Gamepad, 5);
	JoystickButton rb = new JoystickButton(Gamepad, 6);
	JoystickButton lt = new JoystickButton(Gamepad, 7);
  JoystickButton rt = new JoystickButton(Gamepad, 8);
  
  //Buttons on Board (OP Controller)
  JoystickButton defense = new JoystickButton(BoardController, 0);
  JoystickButton high = new JoystickButton(BoardController, 1);
  JoystickButton mid = new JoystickButton(BoardController, 2);
  JoystickButton low = new JoystickButton(BoardController, 3);
  JoystickButton hatch = new JoystickButton(BoardController, 4);
  JoystickButton cargo = new JoystickButton(BoardController, 5);
  JoystickButton home = new JoystickButton(BoardController, 6);
  
  JoystickButton leftRocketFar = new JoystickButton(BoardLeftField, 0);
  JoystickButton leftRocketMid = new JoystickButton(BoardLeftField, 1);
  JoystickButton leftRocketNear = new JoystickButton(BoardLeftField, 2);
  JoystickButton leftCargoShipFar = new JoystickButton(BoardLeftField, 3);
  JoystickButton leftCargoShipMid = new JoystickButton(BoardLeftField, 4);
  JoystickButton leftCargoShipNear = new JoystickButton(BoardLeftField, 5);
  JoystickButton leftCargoShipFront = new JoystickButton(BoardLeftField, 6);
  JoystickButton leftFeedingStation = new JoystickButton(BoardLeftField, 7);

  JoystickButton rightRocketFar = new JoystickButton(BoardRightField, 0);
  JoystickButton rightRocketMid = new JoystickButton(BoardRightField, 1);
  JoystickButton rightRocketNear = new JoystickButton(BoardRightField, 2);
  JoystickButton rightCargoShipFar = new JoystickButton(BoardRightField, 3);
  JoystickButton rightCargoShipMid = new JoystickButton(BoardRightField, 4);
  JoystickButton rightCargoShipNear = new JoystickButton(BoardRightField, 5);
  JoystickButton rightCargoShipFront = new JoystickButton(BoardRightField, 6);
  JoystickButton rightFeedingStation = new JoystickButton(BoardRightField, 7);

  public OI() {
    //Arm testing
    y.whenActive(new Cmd_ArmManualUp());
    a.whenActive(new Cmd_ArmManualDown());
    b.whenActive(new Cmd_ManualArmStop());
    x.whenActive(new Cmd_TestDropRun());
  }

  public void setAllowedHomeButtons() {

  }

  public void setAllowedDefenseButtons() {
    
  }

  public void setAllowedCargoHatchButtons() {
    
  }

  public Joystick getGamepad(){
    return Gamepad;
  }

  public Joystick getBoardController(){
    return BoardController;
  }

  public boolean getButtonState(int btn) {
    return Gamepad.getRawButton(btn);
  }

}
