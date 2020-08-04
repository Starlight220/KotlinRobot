package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Shooter

class KeepVelocity(
    private val velocity: Double,
    private val tolerance: Double = 0.0,
    private val bucketSize: Int = 5
) : CommandBase() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {}

    override fun execute() {
        Shooter.velocity = velocity
    }

    private var count = 0
    override fun isFinished(): Boolean {
        if (StrictMath.abs(velocity - Shooter.velocity) < tolerance) count++ else count = 0
        return count >= bucketSize
    }

    override fun end(interrupted: Boolean) = -Shooter
}
