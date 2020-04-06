package lib

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANPIDController
import edu.wpi.first.wpilibj.controller.PIDController


data class PIDConfig(
        val p : Double,
        val i : Double = 0.0,
        val d : Double = 0.0,
        val aff: Double = 0.0
){
    fun getWPIController(): PIDController {
        return PIDController(p, i, d)
    }

    fun applyREVController(controller : CANPIDController): CANPIDController{
        !controller.setP(p)
        !controller.setI(i)
        !controller.setD(d)
        !controller.setFF(aff)
        return controller
    }
}

//data class ProfiledPIDConfig(val p : Double, val i : Double = 0.0, val d : Double = 0.0,
//                             val aff : Double = 0.0, val iMaxAccum: Double = -1.0,
//                             val dFilter : Double = -1.0) {
//    val baseConfig = PIDConfig(p,i,d)
//
//    fun applyREVController(controller : CANPIDController){
//        baseConfig.applyREVController(controller)
//        controller.ff = aff
//        if(iMaxAccum != -1.0) controller.setIMaxAccum(iMaxAccum, 0)
//        if(dFilter != -1.0) controller.setDFilter(dFilter)
//    }
//
//}
val motorInput = Bounds(-1.0, 1.0)

inline class Bounds(val range: ClosedFloatingPointRange<Double>){
    constructor(min : Double, max : Double) : this(min..max)

    fun apply(value : Double): Double = when(value){
            in range -> value
            in Double.MIN_VALUE..range.start -> range.start
            in range.endInclusive..Double.MAX_VALUE -> range.endInclusive
            else -> value
    }

}

/**
 * Resets the gyro angle.
 */
operator fun AHRS.unaryMinus() = this.reset()


