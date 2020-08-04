package frc.robot.subsystems

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward
import frc.excalibur.lib.math.PIDConfig

typealias DoublePair = Pair<Double, Double>

// drive
const val trackWidth: Double = 0.6
val driveConfig: PIDConfig = PIDConfig(p = 0.1)
val driveFeedforward: SimpleMotorFeedforward = SimpleMotorFeedforward(0.0, 0.0, 0.0)
val driveConversions: DoublePair = Pair(0.4, 1.0)

const val driveAngleTolerance: Double = 0.5
val driveAngleConfig: PIDConfig = PIDConfig(0.1)

// shoot
const val shootVelocity: Double = 10_000.0
val flywheelConfig: PIDConfig = PIDConfig(p = 0.1, aff = 0.1)
const val velocityTolerance: Double = 1_000.0

const val intakeSpeed: Double = 0.4

const val heightTarget: Int = 10_000
