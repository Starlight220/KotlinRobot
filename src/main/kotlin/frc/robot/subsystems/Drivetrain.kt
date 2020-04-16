package frc.robot.subsystems

import com.revrobotics.AlternateEncoderType
import com.revrobotics.CANSparkMax.IdleMode
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.SubsystemBase
import lib.can.*
import lib.command.Invokable
import lib.unaryMinus
import java.util.function.BiConsumer
import java.util.function.Supplier
import com.kauailabs.navx.frc.AHRS as Gyro
import com.revrobotics.CANEncoder as Encoder
import com.revrobotics.CANPIDController as PIDController
import com.revrobotics.CANSparkMax as SparkMax


object Drivetrain : SubsystemBase(), Invokable {
    private val type = MotorType.kBrushless

    private val rightMaster : SparkMax = !SparkMax(rightMasterID, type)
    private val rightSlave : SparkMax = SparkMax(rightSlaveID, type) follows rightMaster
    private val leftMaster : SparkMax = SparkMax(leftMasterID, type)
    private val leftSlave : SparkMax = SparkMax(leftSlaveID, type) follows leftMaster

    object Drive : DifferentialDrive(leftMaster, rightMaster)

    private val gyro = Gyro(gyroPort)

    private val rightController : PIDController = rightMaster.pidController configuredBy driveConfig
    private val leftController : PIDController = leftMaster.pidController configuredBy driveConfig

    private val rightEncoder : Encoder = !rightMaster.getAlternateEncoder(
            AlternateEncoderType.kQuadrature, 2046) * driveConversions
    private val leftEncoder : Encoder =leftMaster.getAlternateEncoder(
            AlternateEncoderType.kQuadrature, 2046) * driveConversions

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
        odometry.update(Rotation2d.fromDegrees(heading), leftDistance, rightDistance)
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
            BiConsumer {left : Double, right : Double ->
                leftController.setReference(left, ControlType.kVoltage)
                rightController.setReference(right, ControlType.kVoltage)},
            this
    )


    override operator fun invoke(){
        initOdometery()
        rightMaster.burnFlash()
        rightSlave.burnFlash()
        leftMaster.burnFlash()
        leftSlave.burnFlash()
    }
}