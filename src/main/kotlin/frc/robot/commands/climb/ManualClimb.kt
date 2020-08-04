package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.climbPower
import frc.robot.subsystems.Climber
import frc.robot.subsystems.Shooter

class ManualClimb : CommandBase() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {}

    override fun execute() {
        Climber.absPower = climbPower
    }

    override fun isFinished(): Boolean = false

    override fun end(interrupted: Boolean) = -Climber
}
