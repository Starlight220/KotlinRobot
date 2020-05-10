package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.DoubleSolenoid.Value
import lib.command.XSubsystem
import lib.devices.can.PCM
import com.ctre.phoenix.motorcontrol.can.VictorSPX as Victor

object Intake : XSubsystem() {
    private val intake : Victor = Victor(intakeID)
    private val piston : DoubleSolenoid = PCM().DoubleSolenoid(intakePistonOpen, intakePistonClosed)

    var active : Boolean = false
        set(value) = intake.set(ControlMode.PercentOutput, if(value) intakeSpeed else 0.0)
    var open : Boolean
        set(value) = piston.set(if(value) Value.kForward else Value.kReverse)
        get() = piston.get() == Value.kForward

    override fun release() = intake.neutralOutput()
}