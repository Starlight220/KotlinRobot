package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj2.command.SubsystemBase
import com.ctre.phoenix.motorcontrol.can.VictorSPX as Victor

object Transporter : SubsystemBase(){
    private val flicker : Victor = Victor(flickerID)
    private val tower : Victor = Victor(towerID)


    private var towerSpeed : Double = 0.0
        set(value) = tower.set(ControlMode.PercentOutput, value)
    private var flickerSpeed : Double = 0.0
        set(value) = flicker.set(ControlMode.PercentOutput, value)

    var mode : TransportMode = TransportMode.OFF
        set(value){
            if(field != value){
                field = value
                value.apply()
            }
        }

    enum class TransportMode(private val flicker : Double, private val tower : Double){
        IN(0.6, 0.6),
        SHOOT(0.6, 0.6),
        EJECT(-0.4, -0.4),
        OFF(0.0, 0.0);
        internal fun apply(){
            towerSpeed = this.tower
            flickerSpeed = this.flicker
        }
    }

    operator fun unaryMinus(){
        towerSpeed = 0.0
        flickerSpeed = 0.0
    }
}