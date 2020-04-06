package frc.robot.subsystems
import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj2.command.SubsystemBase
import lib.plus
import com.ctre.phoenix.motorcontrol.can.TalonSRX as Talon
import com.ctre.phoenix.motorcontrol.can.VictorSPX as Victor

object Climber : SubsystemBase(){
    private val climbMaster : Talon = Talon(climbMasterID)
    private val climbSlave : Victor = Victor(climbSlaveID) + climbMaster

    var height : Int
        get() = climbMaster.selectedSensorPosition
        set(value) = climbMaster.set(ControlMode.Position, value.toDouble())

    var absPower : Double = 0.0
        set(value) = climbMaster.set(ControlMode.PercentOutput, value)

    operator fun unaryMinus() {
        absPower = 0.0
    }

    fun resetEncoder(){
        climbMaster.selectedSensorPosition = 0
    }

    operator fun invoke(){

    }
}