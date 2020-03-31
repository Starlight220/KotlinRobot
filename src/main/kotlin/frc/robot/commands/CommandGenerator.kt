package frc.robot.commands

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.commands.drive.KeepAngle
import frc.robot.commands.shooter.KeepVelocity
import frc.robot.commands.transport.IntoShooter
import frc.robot.subsystems.*

//object CommandGenerator{
fun getShootProcess(shooter: () -> Boolean): Command =
        KeepVelocity(shootVelocity).alongWith(KeepAngle({ Limelight.tx })).alongWith(IntoShooter(shooter))
//}

fun goBackAndShoot() : Command{
    val backUp = Drivetrain.getRamsete(Trajectories["goBackAndShoot"])
    val shootProcess = getShootProcess {
        Shooter.isOnVelocity() && KeepAngle.isOnAngle(Limelight.tx, driveAngleTolerance)}
    val collectBalls = Drivetrain.getRamsete(Trajectories["collectTrenchBalls"])

    return backUp.andThen(shootProcess).andThen(collectBalls)
}

