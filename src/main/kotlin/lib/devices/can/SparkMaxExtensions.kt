package lib.devices.can

import com.revrobotics.CANPIDController
import frc.excalibur.lib.devices.can.not
import frc.excalibur.lib.math.PIDConfig

/**
 * Applies the values of a PIDConfig object to this PID controller.
 * @param config the config object
 * @receiver the PID controller that is configured
 */
infix fun CANPIDController.configuredBy(config : PIDConfig) : CANPIDController =
        config.applyREVController(this)

fun PIDConfig.applyREVController(controller : CANPIDController): CANPIDController {
        !controller.setP(p)
        !controller.setI(i)
        !controller.setD(d)
        !controller.setFF(aff)
        return controller
}
