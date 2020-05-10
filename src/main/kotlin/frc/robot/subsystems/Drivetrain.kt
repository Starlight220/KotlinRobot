@file:Suppress("JoinDeclarationAndAssignment")

package frc.robot.subsystems

import com.revrobotics.AlternateEncoderType
import com.revrobotics.CANSparkMax.IdleMode
import com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless as mt
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj2.command.Command
import lib.util.Cache
import lib.command.XSubsystem
import lib.devices.can.*
import lib.devices.navx.*
import lib.trajectory.RamseteConfig
import kotlin.reflect.KProperty
import com.kauailabs.navx.frc.AHRS as Gyro
import com.revrobotics.CANEncoder as Encoder
import com.revrobotics.CANPIDController as PIDController
import com.revrobotics.CANSparkMax as SparkMax

object Drivetrain : XSubsystem() {
    private val rightMaster: SparkMax
    private val rightSlave: SparkMax
    private val leftMaster: SparkMax
    private val leftSlave: SparkMax

    private val rightEncoder: Encoder
    private val leftEncoder: Encoder

    private val gyro: Gyro

    private val rightController: PIDController
    private val leftController: PIDController

    private val drive: DifferentialDrive

    private lateinit var odometry: DifferentialDriveOdometry

    init {
        rightMaster = !SparkMax(rightMasterID, mt)
        leftMaster = SparkMax(leftMasterID, mt)
        rightSlave = SparkMax(rightSlaveID, mt) follows rightMaster
        leftSlave = SparkMax(leftSlaveID, mt) follows leftMaster

        gyro = Gyro(gyroPort)

        rightEncoder = !rightMaster.getAlternateEncoder(
                AlternateEncoderType.kQuadrature, 2046) * driveConversions
        leftEncoder = leftMaster.getAlternateEncoder(
                AlternateEncoderType.kQuadrature, 2046) * driveConversions

        rightController = rightMaster.pidController configuredBy driveConfig
        leftController = leftMaster.pidController configuredBy driveConfig

        drive = DifferentialDrive(leftMaster, rightMaster)
    }

    var idleMode: IdleMode = IdleMode.kCoast
        set(value) {
            if (value != field) {
                field = value
                rightMaster.idleMode = value
                leftMaster.idleMode = value
                rightSlave.idleMode = value
                leftSlave.idleMode = value
            }
        }
        get() = rightMaster.idleMode

    val angle: Double
        get() = gyro.angle
    val leftDistance: Double
        get() = leftEncoder.position
    val rightDistance: Double
        get() = rightEncoder.position

    fun initOdometry() {
        -rightEncoder
        -leftEncoder
        -gyro
        odometry = DifferentialDriveOdometry(gyro.getRotation())
    }

    override fun init() {
        drive
        initOdometry()
        rightMaster.burnFlash()
        rightSlave.burnFlash()
        leftMaster.burnFlash()
        leftSlave.burnFlash()
    }

    override fun release() {
        drive.stopMotor()
    }

    override fun periodic() {
        odometry.update(gyro.getRotation(), leftDistance, rightDistance)
    }

    fun arcadeDrive(xSpeed: Double, zRotation: Double) = drive.arcadeDrive(xSpeed, zRotation)
    fun tankDrive(left: Double, right: Double) = drive.tankDrive(left, right)

    fun getRamsete(trajectory: Trajectory): Command =
            Cache("rc") {
                RamseteConfig(
                        poseSource = odometry::getPoseMeters,
                        trackWidth = trackWidth,
                        speedConsumer = { left, right ->
                            leftController.setReference(left, ControlType.kVelocity)
                            rightController.setReference(right, ControlType.kVelocity)
                        },
                        drivetrain = this)
            }
                    .getRamsete(trajectory)
}