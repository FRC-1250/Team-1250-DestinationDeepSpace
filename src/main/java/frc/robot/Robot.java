/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.test.Cmd_StartMatch;
import frc.robot.subsystems.Sub_Arm;
import frc.robot.subsystems.Sub_Bars;
import frc.robot.subsystems.Sub_Climber;
import frc.robot.subsystems.Sub_Collector;
import frc.robot.subsystems.Sub_DriveTrain;
import frc.robot.subsystems.Sub_Limelight;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  public static Sub_DriveTrain s_drivetrain = new Sub_DriveTrain();
  public static Sub_Limelight s_limelight = new Sub_Limelight();
  public static Sub_Collector s_collector = new Sub_Collector();
  public static Sub_Climber s_climber = new Sub_Climber();
  public static Sub_Arm s_arm = new Sub_Arm();
  public static Sub_Bars s_bars = new Sub_Bars();
  public static String mode = new String();
  public static Cmd_StartMatch c_startmatch = new Cmd_StartMatch();

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    if(!s_arm.isArmHome() && s_arm.dartMotor0Position() >= 0){
      s_arm.setHomePositionDiscrepencyErrorState(true);
    }
    else {
      s_arm.setHomePositionDiscrepencyErrorState(false);
    }

    m_oi = new OI();
   UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
  camera.setFPS(10);
  camera.setResolution(300, 200);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("ArmPositionRobotPeriodic", s_arm.dartMotor0Position());
    SmartDashboard.putBoolean("IsArmHome??", s_arm.isArmHome());
    SmartDashboard.putNumber("tx", Robot.s_limelight.getCubeX());
    SmartDashboard.putNumber("Drive Ticks", Robot.s_drivetrain.leftPosition());
    SmartDashboard.putNumber("Angle", Robot.s_drivetrain.getGyroAngle());
    SmartDashboard.putData("Commands", Scheduler.getInstance());
    SmartDashboard.putBoolean("TriggerBoolCargo",!m_oi.cargo.get());
    SmartDashboard.putBoolean("TriggerBoolHigh",!m_oi.high.get());
    SmartDashboard.putBoolean("TriggerBoolMid",!m_oi.mid.get());
    SmartDashboard.putBoolean("TriggerBooLow",!m_oi.low.get());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    s_drivetrain.coastMode();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();
    s_drivetrain.brakeMode();
    c_startmatch.start();
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    s_drivetrain.brakeMode();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
