package utils

import com.revrobotics.CANError
import com.revrobotics.CANPIDController
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.controller.PIDController

class ExternalPIDController(private val m_controller : CANPIDController) :
        PIDController(m_controller.p, m_controller.i, m_controller.d){

    var mode : ControlType = ControlType.kPosition
//    var slot : Int = 0 TODO:add slot option?

    override fun setSetpoint(setpoint: Double) {
        m_controller.setReference(setpoint, mode)
                .apply { if(this == CANError.kError) DriverStation.reportError("SparkMax PID communication failed", false) }
    }

    override fun getVelocityError(): Double {
        return super.getVelocityError()
    }

    override fun getD(): Double {
        return m_controller.d
    }

    override fun enableContinuousInput(minimumInput: Double, maximumInput: Double) {
//        super.enableContinuousInput(minimumInput, maximumInput)
        TODO()
    }

    override fun setP(kP: Double) {
//        if (slot == 0)
        m_controller.p = kP
//        else m_controller.setP(kP, slot)
    }

    override fun setPID(Kp: Double, Ki: Double, Kd: Double) {
        p = Kp
        i = Ki
        d = Kd
    }

    override fun setD(Kd: Double) {
        !m_controller.setD(Kd)
    }

    override fun getPositionError(): Double {
        TODO()
    }

    override fun reset() {
        m_controller.setReference(0.0, ControlType.kDutyCycle)
    }

    override fun close() {
        reset()
    }

    override fun getP(): Double {
        return m_controller.p
    }

    override fun getSetpoint(): Double {
        TODO()
    }

    override fun setTolerance(positionTolerance: Double) {
        super.setTolerance(positionTolerance)
    }

    override fun setTolerance(positionTolerance: Double, velocityTolerance: Double) {
        TODO()
//        m_controller.setTolerance(positionTolerance, velocityTolerance)
    }

    override fun atSetpoint(): Boolean {
        return super.atSetpoint()
    }

    override fun getPeriod(): Double {
        return super.getPeriod()
    }

    override fun setIntegratorRange(minimumIntegral: Double, maximumIntegral: Double) {
        super.setIntegratorRange(minimumIntegral, maximumIntegral)
    }

    override fun getI(): Double {
        return super.getI()
    }

    override fun calculate(measurement: Double, setpoint: Double): Double {
        return super.calculate(measurement, setpoint)
    }

    override fun calculate(measurement: Double): Double {
        return super.calculate(measurement)
    }

    override fun setI(Ki: Double) {
        super.setI(Ki)
    }

    override fun disableContinuousInput() {
        super.disableContinuousInput()
    }
}