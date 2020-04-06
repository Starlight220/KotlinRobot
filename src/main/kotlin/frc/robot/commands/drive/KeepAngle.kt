package frc.robot.commands.drive

import com.revrobotics.CANSparkMax.IdleMode
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.driveAngleConfig
import java.lang.StrictMath.abs

class KeepAngle(
        private val errorSupplier : () -> Double,
        private val tolerance : Double = 0.0,
        private val bucketSize : Int = 5,
        private val xSpeed : () -> Double = {0.0},
        private val endMode: IdleMode = IdleMode.kBrake
) : CommandBase() {

    companion object{
        fun isOnAngle(error : Double, tolerance: Double) : Boolean = abs(error) < tolerance
    }

    private val controller : PIDController

    init {
        addRequirements(Drivetrain)
        controller = driveAngleConfig.getWPIController()
    }

    override fun initialize() {
        controller.setpoint = 0.0
    }

    override fun execute() {
        Drivetrain.Drive.arcadeDrive(xSpeed(), controller.calculate(errorSupplier()))
    }

    var count = 0
    override fun isFinished(): Boolean {
        if(isOnAngle(errorSupplier(), tolerance)) count++ else count = 0
        return count >= bucketSize
    }

    override fun end(interrupted: Boolean) {
        -Drivetrain
        Drivetrain.idleMode = endMode
    }
}