package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.Command
import lib.command.andThen
import frc.robot.commands.drive.KeepAngle
import frc.robot.commands.shooter.KeepVelocity
import frc.robot.commands.transport.IntoShooter
import frc.robot.subsystems.*
import lib.trajectory.TrajectorySource.Trajectories

fun getShootProcess(shooter: () -> Boolean): Command =
        KeepVelocity(shootVelocity).alongWith(KeepAngle({ Limelight.tx }), IntoShooter(shooter))

fun goBackAndShoot() : Command {
    val backUp = Drivetrain.getRamsete(Trajectories["goBackAndShoot"])
    val shootProcess = getShootProcess {
        Shooter.isOnVelocity() && KeepAngle.isOnAngle(Limelight.tx, driveAngleTolerance)
    }
    val collectBalls = Drivetrain.getRamsete(Trajectories["collectTrenchBalls"])

    return backUp.andThen(shootProcess, collectBalls)
}

