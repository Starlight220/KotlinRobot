package frc.robot.subsystems

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward
import lib.PIDConfig

typealias DoublePair = Pair<Double, Double>

//drive
const val trackWidth = 0.6
val driveConfig = PIDConfig(p = 0.1)
val driveFeedforward = SimpleMotorFeedforward(0.0, 0.0, 0.0)
val driveConversions: DoublePair = Pair(0.4, 1.0)

const val driveAngleTolerance = 0.5
val driveAngleConfig = PIDConfig(0.1)

//shoot
const val shootVelocity = 10_000.0
val flywheelConfig = PIDConfig(p = 0.1, aff = 0.1)
const val velocityTolerance = 1_000.0

const val intakeSpeed = 0.4

const val heightTarget = 10_000
