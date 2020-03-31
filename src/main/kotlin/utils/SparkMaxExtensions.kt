package utils

import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMax.ExternalFollower.kFollowerDisabled
import com.revrobotics.ControlType
import com.revrobotics.CANError as Error
import com.revrobotics.CANSparkMax as SparkMax
import edu.wpi.first.wpilibj.DriverStation as DS
import com.revrobotics.CANEncoder as Encoder

typealias DoublePair = Pair<Double, Double>

fun CANPIDController.setAbsolutePercent(percent : Double) = !setReference(percent, ControlType.kDutyCycle)

operator fun SparkMax.not() : SparkMax{
    this.inverted = true
    return this
}
operator fun Encoder.not() : Encoder{
    !this.setInverted(true)
    return this
}

operator fun CANPIDController.plus(config : PIDConfig) : CANPIDController =  config.applyREVController(this)

operator fun Error.not(){
    if(this != Error.kOk){
        DS.reportError("SparkMax config failed : $this", true)
    }
}

operator fun SparkMax.plus(master: SparkMax) : SparkMax{
    !this.follow(master)
    return this
}
operator fun SparkMax.minus(master: SparkMax) : SparkMax{
    !this.follow(master, true)
    return this
}

operator fun Encoder.times(factors : DoublePair) : Encoder{
    val (posFactor, velFactor) = factors
    this.positionConversionFactor = posFactor
    this.velocityConversionFactor = velFactor
    return this
}
//reset operators
operator fun SparkMax.unaryMinus(): SparkMax{
    -this.alternateEncoder
    !this.restoreFactoryDefaults(false)
    !this.follow(kFollowerDisabled, 0)
    return this
}
operator fun Encoder.unaryMinus(){
    !this.setPosition(0.0)
}

