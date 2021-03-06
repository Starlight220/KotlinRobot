/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.RunCommand
import frc.excalibur.lib.test.TestVector
import frc.robot.commands.auto.Trajectories
import frc.robot.commands.auto.goBackAndShoot
import frc.robot.commands.intake.IntakeDrive
import frc.robot.commands.transport.TransportDrive
import frc.robot.subsystems.*

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
object Robot : TimedRobot() {
    private lateinit var autoCommand: Command

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    override fun robotInit() {
        Drivetrain
        Intake
        Shooter
        Transporter
        Climber

        Limelight

        Trajectories

//        autoCommand = initAutoCommand()
    }

    private fun initDefaultCommands() {
        Drivetrain.defaultCommand = RunCommand(Runnable {
            Drivetrain.arcadeDrive(xSpeedAxis, zRotateAxis)
        }, Drivetrain)
        Intake.defaultCommand = IntakeDrive()
        Transporter.defaultCommand = TransportDrive()
    }

    private fun initAutoCommand(): Command {
        return goBackAndShoot()
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     *
     * This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     *
     * You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    override fun autonomousInit() {
        Drivetrain.initOdometry()
        autoCommand.schedule()
    }

    /**
     * This function is called periodically during autonomous.
     */
    override fun autonomousPeriodic() {
    }

    override fun teleopInit() {
        initDefaultCommands()
    }

    /**
     * This function is called periodically during operator control.
     */
    override fun teleopPeriodic() {}

    override fun testInit() {
        TestVector.TestThread()
    }
}
