package frc.robot.commands.transport

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Transporter

class IntoShooter(val shooter: () -> Boolean) : CommandBase() {
    init {
        addRequirements(Transporter)
    }

    override fun initialize() {
    }

    override fun execute() {
        Transporter.mode = if (shooter()) Transporter.TransportMode.SHOOT else Transporter.TransportMode.OFF
    }

    override fun isFinished(): Boolean = false

    override fun end(interrupted: Boolean) = -Transporter
}
