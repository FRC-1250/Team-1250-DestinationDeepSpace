/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.arm.Cmd_ArmJog;

public class Sub_Arm extends Subsystem {
  

  WPI_TalonSRX dartMotor0 = new WPI_TalonSRX(RobotMap.ARM_DART0);
  WPI_TalonSRX dartMotor1 = new WPI_TalonSRX(RobotMap.ARM_DART1);
  DigitalInput armHomeSensor = new DigitalInput(RobotMap.ARM_HOME);

  //Group for voltage control outputs
  private SpeedController gDartDrive = new SpeedControllerGroup(dartMotor0, dartMotor1);

  //-----------------------------------
  //Arm position locations minus the high cargo because we can't do that with the current arm.
  public double highHatchPos = -555655; //75 inches
  public double midHatchPos = -362442; //47 inches
  public double lowHatchPos = -8845; //19 inches same as loading station and cargoship
  public double lowCargoPos = -291195; //27.50 inches
  public double midCargoPos = -508961; //55.50 inches
  public double highCargoPos = 0; //83.50 inches DO NOT ATTEMPT YET UNTIL ARM REDONE
  public double shipCargoPos = -363422; //39.625 inches
  public double home = 0; //Home is home
  //------------------------------------

  double armSetpoint0;

  public Sub_Arm(){
    //General config for both motors on the dart drives for the arm
    //Not set to following because we wanted individual control for both sides

    //Config For Encoder
    dartMotor0.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    dartMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    //PID and other motor config for dart motor 0
    dartMotor0.configNominalOutputForward(0, 10);
		dartMotor0.configNominalOutputReverse(0, 10);
		dartMotor0.configPeakOutputForward(1, 10);
		dartMotor0.configPeakOutputReverse(-1, 10);
		dartMotor0.config_kF(0, 0.0, 10);
		dartMotor0.config_kP(0, 0.06, 10);
		dartMotor0.config_kI(0, 0.002, 10);
		dartMotor0.config_kD(0, 0, 10);
    dartMotor0.config_IntegralZone(0, 2000, 10);
    
    //PID and other motor config for dart motor 1
    dartMotor1.configNominalOutputForward(0, 10);
		dartMotor1.configNominalOutputReverse(0, 10);
		dartMotor1.configPeakOutputForward(1, 10);
		dartMotor1.configPeakOutputReverse(-1, 10);
		dartMotor1.config_kF(0, 0.0, 10);
		dartMotor1.config_kP(0, 0.06, 10);
		dartMotor1.config_kI(0, 0.002, 10);
		dartMotor1.config_kD(0, 0, 10);
    dartMotor1.config_IntegralZone(0, 2000, 10);
    
    //Inversion for both motors
    dartMotor0.setInverted(false);
    dartMotor1.setInverted(false);

    //Neutral mode config
    dartMotor0.setNeutralMode(NeutralMode.Brake);
    dartMotor1.setNeutralMode(NeutralMode.Brake);
  }

  //Encode position for both sides
  public double dartMotor0Position(){
    return dartMotor0.getSelectedSensorPosition();
  }
  public double dartMotor1Position(){
    return dartMotor1.getSelectedSensorPosition();
  }

  //Reset the arm position to 0 for when it gets home
  public void resetArmPos(){
    dartMotor0.setSelectedSensorPosition(0);
    dartMotor1.setSelectedSensorPosition(0);
  }

  //-----------------------------------
  //Basic motor voltage outputs for jogging the arm currently
  //May need to be switched to velocity control
  public void dartDriveGoDown(){
    gDartDrive.set(1);
  }

  public void dartDriveGoUp(){
    gDartDrive.set(-1);
  }
  //-----------------------------------

  //Arm stopping method to halt movement
  public void armStop(){
    gDartDrive.set(0);
  }

  //Returns false when sensor is tripped
  public boolean isArmHome(){
    return armHomeSensor.get();
  }

  //-----------------------------------
  //Velocity control for the arm to keep both sides in sync even is the motors aren't the same power
  //TODO: Velocity control needs to be tested
  public void dartVelocitySetUp(){
    dartMotor0.set(ControlMode.Velocity, -555565);
    dartMotor1.set(ControlMode.Velocity, -555565);
  }

  public void dartVelocitySetDown(){
    dartMotor0.set(ControlMode.Velocity, 555565);
    dartMotor1.set(ControlMode.Velocity, 555565);
  }

  public void dartVelocityStop(){
    dartMotor0.set(ControlMode.Velocity, 0);
    dartMotor1.set(ControlMode.Velocity, 0);
  }
  //-----------------------------------

  //-----------------------------------
  //All of the methods for every position the arm needs to go to plus a method that can accept values
  //in the command that it is used in. I decided to not use this for everyone, and make seperate methods for every position
  //because the call looked cleaner to me in the command

  //Allows a position to be passed in from any command it is needed in
  public void setArmPosTest(double pos){
    dartMotor0.set(ControlMode.Position, pos);
    dartMotor1.set(ControlMode.Position, pos);
  }
  //High cargo position - WILL NOT WORK, WILL JUST RETURN THE ARM TO HOME
  public void setArmCargoHigh(){
    dartMotor0.set(ControlMode.Position, highCargoPos);
    dartMotor1.set(ControlMode.Position, highCargoPos); 
  }

  //Mid cargo position
  public void setArmCargoMid(){
    dartMotor0.set(ControlMode.Position, midCargoPos);
    dartMotor1.set(ControlMode.Position, midCargoPos); 
  }

  //Low cargo position
  public void setArmCargoLow(){
    dartMotor0.set(ControlMode.Position, lowCargoPos);
    dartMotor1.set(ControlMode.Position, lowCargoPos); 
  }

  //High hatch position
  public void setArmHatchHigh(){
    dartMotor0.set(ControlMode.Position, highHatchPos);
    dartMotor1.set(ControlMode.Position, highHatchPos); 
  }

  //Mid hatch position
  public void setArmHatchMid(){
    dartMotor0.set(ControlMode.Position, midHatchPos);
    dartMotor1.set(ControlMode.Position, midHatchPos); 
  }

  //Low hatch position
  public void setArmHatchLow(){
    dartMotor0.set(ControlMode.Position, lowHatchPos);
    dartMotor1.set(ControlMode.Position, lowHatchPos); 
  }

  //Cargo ship cargo position
  public void setArmCargoShip(){
    dartMotor0.set(ControlMode.Position, shipCargoPos);
    dartMotor1.set(ControlMode.Position, shipCargoPos);
  }

  @Override
  public void initDefaultCommand() {
    //Default command for manual arm control
    setDefaultCommand(new Cmd_ArmJog());
  }
}
