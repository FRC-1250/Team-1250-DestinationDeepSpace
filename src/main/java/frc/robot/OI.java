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
import frc.robot.commands.groups.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

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

  Trigger cargoHigh = new Trigger() {
    @Override 
    public boolean get() 
    { return !cargo.get() && !high.get(); }
  };

  Trigger cargoMid = new Trigger() {
    @Override 
    public boolean get() 
    { return !cargo.get() && !mid.get(); }
  };

   Trigger cargoLow = new Trigger() {
   @Override 
   public boolean get() 
   { return !cargo.get() && !low.get(); }
  };

  Trigger hatchHigh = new Trigger() {
    @Override 
    public boolean get() 
    { return !hatch.get() && !high.get(); }
  };

  Trigger hatchMid = new Trigger() {
    @Override 
    public boolean get() 
    { return !hatch.get() && !mid.get(); }
  };

   Trigger hatchLow = new Trigger() {
   @Override 
   public boolean get() 
   { return !hatch.get() && !low.get(); }
  };

  public OI() {

    lb.whenActive(new CmdI_CollectorHatchTongueExtend());
    rb.whenActive(new CmdI_CollectorHatchFullPlace());
    // a.whenActive(new CmdI_CollectorHatchTongueRetract());
    y.whenActive(new Cmd_CollectorInput());
    x.whenActive(new CmdG_CollectorFullCollectWithTiming());
    b.whenActive(new CmdT_CollectorArmIntakeSpit(1));

    cargoHigh.whenActive(new Cmd_ArmCargoHigh());
    cargoMid.whenActive(new Cmd_ArmCargoMid());
    cargoLow.whenActive(new Cmd_ArmCargoLow());

    hatchHigh.whenActive(new Cmd_ArmHatchHigh());
    hatchMid.whenActive(new Cmd_ArmHatchMid());
    hatchLow.whenActive(new CmdG_Home());

    home.whenInactive(new CmdG_Home());

    //Arm testing
    // a.whenActive(new Cmd_AutoDrive(-150, 0.3, 0.6));
    // y.whenActive(new Cmd_AutoTurn(-35, 0.2, 0.4));
    // b.whenActive(new Cmd_AutoDrive(-20, 0.3, 0.5));
    // x.whenActive(new Cmd_AutoTurn(35, 0.2, 0.4));
    // rb.whenActive(new Cmd_AutoDrive(-45, 0.2, 0.4));
    // lb.whenActive(new Cmd_AutoTurn(80, 0.2, 0.4));
    // rt.whenActive(new Cmd_TrackingDrive(100, 0.4, 0.2));

    a.whenActive(new CmdG_AutoRightHatchRightHab2());
    // b.whenActive(new CmdG_Hab2Climb());
    
    // y.whenActive(new CmdG_CollectorFullCollectWithTiming());
    //x.whenActive(new CmdI_CollectorHatchTongueRetract());
    //y.whenActive(new CmdI_CollectorHatchTongueExtend());
    //b.whenActive(new CmdI_CollectorHatchFullPlace());
    //a.whenActive(new CmdG_CollectorFullCollectWithTiming());
    //rb.whenActive(new CmdT_CollectorArmIntakeSpit(1));

    // lb.whenPressed(new Cmd_CollectorInput());
     // b.whenActive(new CmdI_ResetArmZero());    // b.whenActive(new Cmd_CollectorDropMotors());
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
  
  public boolean getButtonStateBoard(int btn) {
    return BoardController.getRawButton(btn);
  }
}
