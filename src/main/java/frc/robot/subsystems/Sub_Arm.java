/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.arm.Cmd_ArmJog;

public class Sub_Arm extends Subsystem {
  

  CANSparkMax dartMotor0 = new CANSparkMax(RobotMap.ARM_DART0, MotorType.kBrushless);
  CANSparkMax dartMotor1 = new CANSparkMax(RobotMap.ARM_DART1, MotorType.kBrushless);
  DigitalInput armHomeSensor = new DigitalInput(RobotMap.ARM_HOME);
  boolean homePositionDiscrepencyErrorState = false;

  public CANPIDController pid0 = new CANPIDController(dartMotor0);
  public CANPIDController pid1 = new CANPIDController(dartMotor1);

  public CANEncoder encoder0 = new CANEncoder(dartMotor0);
  public CANEncoder encoder1 = new CANEncoder(dartMotor1);



  //Group for voltage control outputs
  private SpeedController gDartDrive = new SpeedControllerGroup(dartMotor0, dartMotor1);

  //-----------------------------------
  //Arm position locations minus the high cargo because we can't do that with the current arm.
  public double highHatchPos = -516958; //75 inches
  public double midHatchPos = -305000; //47 inches
  public double lowHatchPos = -8845; //19 inches same as loading station and cargoship
  public double lowCargoPos = -245660; //27.50 inches
  public double midCargoPos = -470451; //55.50 inches
  public double highCargoPos = -657072; //83.50 inches DO NOT ATTEMPT YET UNTIL ARM REDONE
  public double shipCargoPos = -363422; //39.625 inches
  public double home = 0; //Home is home
  //------------------------------------

  

  double armSetpoint0;

  public Sub_Arm(){
    //General config for both motors on the dart drives for the arm
    //Not set to following because we wanted individual control for both sides

    //PID and other motor config for dart motor 0
    pid0.setP(0.06);
    pid0.setI(0.002);
    pid0.setD(0);
    pid0.setIZone(2000);
    
    //PID and other motor config for dart motor 1
    pid1.setP(0.06);
    pid1.setI(0.002);
    pid1.setD(0);
    pid1.setIZone(2000);

    //Inversion for both motors
    dartMotor0.setInverted(true);
    dartMotor1.setInverted(true);

    //Neutral mode config
    dartMotor0.setIdleMode(IdleMode.kBrake);
    dartMotor1.setIdleMode(IdleMode.kBrake);
  }

  //Encode position for both sides
  public double dartMotor0Position(){
    return encoder0.getPosition();
  }
  public double dartMotor1Position(){
    return encoder1.getPosition();
  }

  //Reset the arm position to 0 for when it gets home
  public void resetArmPos(){
    // dartMotor0.setSelectedSensorPosition(0);
    // dartMotor1.setSelectedSensorPosition(0);
    encoder0.setPosition(0);
    encoder1.setPosition(0);

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

  public void dartDriveGoDownDevLeft(){
    dartMotor0.set(.2);
  }

  public void dartDriveGoUpDevLeft(){
    dartMotor0.set(-.2);
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
  // public void dartVelocitySetUp(){
  //   dartMotor0.set(ControlMode.Velocity, -55565);
  //   dartMotor1.set(ControlMode.Velocity, -55565);
  // }

  // public void dartVelocitySetDown(){
  //   dartMotor0.set(ControlMode.Velocity, 55565);
  //   dartMotor1.set(ControlMode.Velocity, 55565);
  // }

  // public void dartVelocityStop(){
  //   dartMotor0.set(ControlMode.Velocity, 0);
  //   dartMotor1.set(ControlMode.Velocity, 0);
  // }
  //-----------------------------------

  //-----------------------------------
  //All of the methods for every position the arm needs to go to plus a method that can accept values
  //in the command that it is used in. I decided to not use this for everyone, and make seperate methods for every position
  //because the call looked cleaner to me in the command

  //Allows a position to be passed in from any command it is needed in
  public void setArmPosTest(double pos){
    pid0.setReference(pos, ControlType.kPosition);
    pid1.setReference(pos, ControlType.kPosition);   
  }
  //High cargo position - WILL NOT WORK, WILL JUST RETURN THE ARM TO HOME
  public void setArmCargoHigh(){
    pid0.setReference(highCargoPos, ControlType.kPosition);
    pid1.setReference(highCargoPos, ControlType.kPosition);
  }

  //Mid cargo position
  public void setArmCargoMid(){
    pid0.setReference(midCargoPos, ControlType.kPosition);
    pid1.setReference(midCargoPos, ControlType.kPosition);
  }

  //Low cargo position
  public void setArmCargoLow(){
    pid0.setReference(lowCargoPos, ControlType.kPosition);
    pid1.setReference(lowCargoPos, ControlType.kPosition);
  }

  //High hatch position
  public void setArmHatchHigh(){
    pid0.setReference(highHatchPos, ControlType.kPosition);
    pid1.setReference(highHatchPos, ControlType.kPosition);
  }

  //Mid hatch position
  public void setArmHatchMid(){
    pid0.setReference(midHatchPos, ControlType.kPosition);
    pid1.setReference(midHatchPos, ControlType.kPosition);
  }

  //Low hatch position
  public void setArmHatchLow(){
    pid0.setReference(lowHatchPos, ControlType.kPosition);
    pid1.setReference(lowHatchPos, ControlType.kPosition);
  }

  //Cargo ship cargo position
  public void setArmCargoShip(){
    pid0.setReference(shipCargoPos, ControlType.kPosition);
    pid1.setReference(shipCargoPos, ControlType.kPosition);
  }

  @Override
  public void initDefaultCommand() {
    //Default command for manual arm control
    setDefaultCommand(new Cmd_ArmJog());
  }

  public void setHomePositionDiscrepencyErrorState(boolean state) {
      SmartDashboard.putBoolean("homePositionDiscrepencyErrorState", state);
      homePositionDiscrepencyErrorState = state;
  }

  public boolean getHomePositionDiscrepencyErrorState() {
    return homePositionDiscrepencyErrorState;
  }
}
