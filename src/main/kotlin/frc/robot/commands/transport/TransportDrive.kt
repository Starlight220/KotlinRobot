package frc.robot.commands.transport

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.ejectTransport
import frc.robot.intakeActive
import frc.robot.intakeClosed
import frc.robot.intakeOpen
import frc.robot.subsystems.Intake
import frc.robot.subsystems.Transporter

class TransportDrive : CommandBase(){
    init{
        addRequirements(Transporter)
    }

    override fun initialize() {}

    override fun execute() {
        Transporter.mode = when{
            ejectTransport -> Transporter.TransportMode.EJECT
            intakeActive -> Transporter.TransportMode.IN
            else -> Transporter.TransportMode.OFF
        }
    }

    override fun isFinished(): Boolean = false


    override fun end(interrupted: Boolean) {}
}