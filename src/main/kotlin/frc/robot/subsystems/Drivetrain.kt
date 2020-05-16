package frc.robot.subsystems

import com.revrobotics.AlternateEncoderType
import com.revrobotics.CANSparkMax.IdleMode
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj2.command.Command
import frc.excalibur.lib.util.Cache
import frc.excalibur.lib.command.XSubsystem
import frc.excalibur.lib.devices.can.*
import frc.excalibur.lib.devices.navx.*
import frc.excalibur.lib.trajectory.RamseteConfig
import com.kauailabs.navx.frc.AHRS as Gyro
import com.revrobotics.CANEncoder as Encoder
import com.revrobotics.CANPIDController as PIDController
import com.revrobotics.CANSparkMax as SparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless as mt

object Drivetrain : XSubsystem() {
    private val rightMaster: SparkMax = !SparkMax(rightMasterID, mt)
    private val rightSlave: SparkMax = SparkMax(rightSlaveID, mt) follows rightMaster
    private val leftMaster: SparkMax = SparkMax(leftMasterID, mt)
    private val leftSlave: SparkMax = SparkMax(leftSlaveID, mt) follows leftMaster

    private val rightEncoder: Encoder
    private val leftEncoder: Encoder

    private val gyro: Gyro = Gyro(gyroPort)

    private val rightController: PIDController
    private val leftController: PIDController

    private val drive: DifferentialDrive

    private lateinit var odometry: DifferentialDriveOdometry

    init {
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