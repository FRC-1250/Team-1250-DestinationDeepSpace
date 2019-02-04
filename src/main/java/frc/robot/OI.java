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

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //Controllers for driver (Gamepad) and for operator(Board)

  Joystick Gamepad = new Joystick(0);
  Joystick Board = new Joystick(1);

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

  public OI() {

    y.whenActive(new Cmd_ArmManualUp());
    a.whenActive(new Cmd_ArmManualDown());
    b.whenActive(new Cmd_ManualArmStop());
    x.whenActive(new Cmd_TestDropRun());



  }



  public Joystick getGamepad(){
    return Gamepad;
  }

  public boolean getButtonState(int btn) {
    return Gamepad.getRawButton(btn);
}



public Joystick getBoard() {
    return Board;
}

}
