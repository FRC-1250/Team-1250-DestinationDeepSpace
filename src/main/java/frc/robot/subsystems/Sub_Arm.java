/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.arm.Cmd_ArmJog;


/**
 * Add your docs here.
 */
public class Sub_Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX dartMotor0 = new WPI_TalonSRX(RobotMap.ARM_DART0);
  WPI_TalonSRX dartMotor1 = new WPI_TalonSRX(RobotMap.ARM_DART1);
  DigitalInput armHomeSensor = new DigitalInput(RobotMap.ARM_HOME);

  private SpeedController gDartDrive = new SpeedControllerGroup(dartMotor0, dartMotor1);

  public static double accumError = 0;
	private final double KP_SIMPLE = 0.05;
  private final double KI_SIMPLE = 0.03;

  //-----------------------------------
  // TODO: Figure out the math of this
  public final double ARM_TICKS = 0;

  //Place holders for arm positions :)))
  public double highHatchPos = 0;
  public double midHatchPos = 0;
  public double lowHatchPos = 0;
  public double lowCargoPos = 0;
  public double midCargoPos = 0;
  public double highCargoPos = 0;
  public double shipCargoPos = 0;
  //------------------------------------

  double armSetpoint0;

  public Sub_Arm(){
    dartMotor0.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    dartMotor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

    dartMotor0.configNominalOutputForward(0, 10);
		dartMotor0.configNominalOutputReverse(0, 10);
		dartMotor0.configPeakOutputForward(.8, 10);
		dartMotor0.configPeakOutputReverse(-.8, 10);
		dartMotor0.config_kF(0, 0.0, 10);
		dartMotor0.config_kP(0, 1, 10);
		dartMotor0.config_kI(0, 0, 10);
		dartMotor0.config_kD(0, 0, 10);
    dartMotor0.config_IntegralZone(0, 0, 10);
    
    dartMotor1.configNominalOutputForward(0, 10);
		dartMotor1.configNominalOutputReverse(0, 10);
		dartMotor1.configPeakOutputForward(.8, 10);
		dartMotor1.configPeakOutputReverse(-.8, 10);
		dartMotor1.config_kF(0, 0.0, 10);
		dartMotor1.config_kP(0, 1, 10);
		dartMotor1.config_kI(0, 0, 10);
		dartMotor1.config_kD(0, 0, 10);
		dartMotor1.config_IntegralZone(0, 0, 10);
  }

  public double dartMotor0Position(){
    return dartMotor0.getSelectedSensorPosition();
  }
  public double dartMotor1Position(){
    return dartMotor1.getSelectedSensorPosition();
  }

  public void resetArmPos(){
    dartMotor0.setSelectedSensorPosition(0);
    dartMotor1.setSelectedSensorPosition(0);
  }

  public void dartDriveGoDown(){
    gDartDrive.set(-1);
  }

  public void dartDriveGoUp(){
    gDartDrive.set(1);
  }


  public void armStop(){
    gDartDrive.set(0);
  }

  public boolean isArmHome(){
    return armHomeSensor.get();
  }
  


  public void setArmPosTest(double pos){
    armSetpoint0 = (int) (ARM_TICKS * pos);
    dartMotor0.set(ControlMode.Position, armSetpoint0);
    dartMotor1.set(ControlMode.Position, armSetpoint0);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new Cmd_ArmJog());
  }
}
