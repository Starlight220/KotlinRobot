package frc.robot.commands.intake

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.intakeActive
import frc.robot.intakeClosed
import frc.robot.intakeOpen
import frc.robot.subsystems.Intake

class IntakeDrive : CommandBase(){
    init{
        addRequirements(Intake)
    }

    override fun initialize() {}

    override fun execute() {
        Intake.active = intakeActive
        Intake.open = !intakeClosed && intakeOpen
    }

    override fun isFinished(): Boolean = false


    override fun end(interrupted: Boolean) {}
}