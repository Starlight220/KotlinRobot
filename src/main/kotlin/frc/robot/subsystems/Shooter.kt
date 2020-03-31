package frc.robot.subsystems
import com.revrobotics.CANEncoder as Encoder
import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj2.command.SubsystemBase
import utils.*
import com.revrobotics.CANSparkMax as SparkMax

object Shooter : SubsystemBase(){
    private val flywheel : SparkMax = SparkMax(flywheelID, MotorType.kBrushless)
    private val encoder : Encoder = flywheel.encoder
    private val controller : CANPIDController = flywheel.pidController + flywheelConfig
    private var lastVelRef : Double = 0.0

    var velocity : Double
        get() = encoder.velocity
        set(value){
            !controller.setReference(value, ControlType.kVelocity)
            lastVelRef = value
        }

    operator fun unaryMinus(){
        flywheel.set(0.0)
    }

    fun isOnVelocity() : Boolean {
        return (lastVelRef - velocity) < velocityTolerance
    }
}