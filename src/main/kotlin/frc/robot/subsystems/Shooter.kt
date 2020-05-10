package frc.robot.subsystems
import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.ControlType
import lib.command.XSubsystem
import lib.devices.can.configuredBy
import lib.devices.can.not
import com.revrobotics.CANEncoder as Encoder
import com.revrobotics.CANSparkMax as SparkMax

object Shooter : XSubsystem(){
    private val flywheel : SparkMax = SparkMax(flywheelID, MotorType.kBrushless)
    private val encoder : Encoder = flywheel.encoder
    private val controller : CANPIDController = flywheel.pidController configuredBy flywheelConfig
    private var lastVelRef : Double = 0.0

    var velocity : Double
        get() = encoder.velocity
        set(value){
            !controller.setReference(value, ControlType.kVelocity)
            lastVelRef = value
        }

    override fun release(){
        flywheel.set(0.0)
    }

    fun isOnVelocity() : Boolean {
        return (lastVelRef - velocity) < velocityTolerance
    }

    override fun init(){
        flywheel.burnFlash()
    }
}