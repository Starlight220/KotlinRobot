package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import frc.excalibur.lib.devices.can.follows
import frc.excalibur.lib.test.withName
import frc.excalibur.lib.command.XSubsystem
import com.ctre.phoenix.motorcontrol.can.TalonSRX as Talon
import com.ctre.phoenix.motorcontrol.can.VictorSPX as Victor

object Climber : XSubsystem() {
    private val climbMaster : Talon = Talon(climbMasterID)
    private val climbSlave : Victor = Victor(climbSlaveID) follows climbMaster

    var height : Int
        get() = climbMaster.selectedSensorPosition
        set(value) = climbMaster.set(ControlMode.Position, value.toDouble())

    var absPower : Double = 0.0
        set(value) = climbMaster.set(ControlMode.PercentOutput, value)

    override fun release(): Unit = climbMaster.neutralOutput()

    override fun init(){
        climbMaster.selectedSensorPosition = 0
    }

    override fun initTestVectors() {
        {pow : Double -> climbMaster.set(ControlMode.Position, pow)} withName "climbMaster"
        {pow : Double -> climbSlave.set(ControlMode.Position, pow)} withName "climbSlave"
    }

}