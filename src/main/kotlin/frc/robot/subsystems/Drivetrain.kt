package frc.robot.subsystems

import com.revrobotics.AlternateEncoderType
import com.revrobotics.CANEncoder as Encoder
import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMax as SparkMax
import com.kauailabs.navx.frc.AHRS as Gyro
import com.revrobotics.CANSparkMax.IdleMode
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.SubsystemBase
import utils.*
import utils.times
import java.util.function.BiConsumer
import java.util.function.Supplier


object Drivetrain : SubsystemBase() {
    private val rightMaster : SparkMax = !SparkMax(rightMasterID, MotorType.kBrushless)
    private val rightSlave : SparkMax = SparkMax(rightSlaveID, MotorType.kBrushless) + rightMaster
    private val leftMaster : SparkMax = SparkMax(leftMasterID, MotorType.kBrushless)
    private val leftSlave : SparkMax = SparkMax(leftSlaveID, MotorType.kBrushless) + leftMaster

    object Drive : DifferentialDrive(leftMaster, rightMaster)

    private val gyro = Gyro(SPI.Port.kMXP)

    private val rightController : CANPIDController = rightMaster.pidController + driveConfig
    private val leftController : CANPIDController = leftMaster.pidController + driveConfig

    private val rightEncoder : Encoder =
            !rightMaster.getAlternateEncoder(AlternateEncoderType.kQuadrature, 2046) * driveConversions
    private val leftEncoder : Encoder =
            leftMaster.getAlternateEncoder(AlternateEncoderType.kQuadrature, 2046) * driveConversions

    private val kinematics : DifferentialDriveKinematics = DifferentialDriveKinematics(trackWidth)
    private lateinit var odometry : DifferentialDriveOdometry

    var idleMode : IdleMode = IdleMode.kCoast
        set(value){
            if(value != field){
                field = value
                rightMaster.idleMode = value
                leftMaster.idleMode = value
                rightSlave.idleMode = value
                leftSlave.idleMode = value
            }
        }
        get() = rightMaster.idleMode

    val heading : Double
        get() = gyro.angle
    val leftDistance : Double
        get() = leftEncoder.position
    val rightDistance : Double
        get() = rightEncoder.position

    fun resetEncoders(){
        -rightEncoder
        -leftEncoder
    }

    fun resetGyro() = -gyro

    fun initOdometery(){
        resetEncoders()
        resetGyro()
        odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(heading))
    }
    operator fun unaryMinus(){
        Drive.tankDrive(0.0, 0.0)
    }

    override fun periodic() {
        odometry.update(Rotation2d.fromDegrees(heading), leftDistance, rightDistance);
    }


    fun getRamsete(trajectory: Trajectory): RamseteCommand = RamseteCommand(
            trajectory,
            Supplier{ odometry.poseMeters},
            RamseteController(),
            driveFeedforward,
            kinematics,
            Supplier{DifferentialDriveWheelSpeeds(leftEncoder.velocity, rightEncoder.velocity)},
            driveConfig.getWPIController(),
            driveConfig.getWPIController(),
            BiConsumer {left : Double, right : Double -> leftController.setReference(left, ControlType.kVoltage)
                rightController.setReference(right, ControlType.kVoltage)},
            this
    )


}