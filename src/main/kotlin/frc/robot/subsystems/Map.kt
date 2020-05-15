package frc.robot.subsystems
import edu.wpi.first.wpilibj.SPI
import frc.excalibur.lib.ports.CAN
import frc.excalibur.lib.ports.PCM
import frc.excalibur.lib.ports.navX

val rightMasterID: Int = CAN[10]
val rightSlaveID: Int = CAN[11]
val leftMasterID: Int = CAN[12]
val leftSlaveID: Int = CAN[13]
val gyroPort: SPI.Port = navX.SPI

val flywheelID: Int = CAN[20]

val intakeID: Int = CAN[30]
val intakePistonClosed: Int = PCM[0]
val intakePistonOpen: Int = PCM[1]

val climbMasterID: Int = CAN[40]
val climbSlaveID: Int = CAN[41]

val flickerID: Int = CAN[50]
val towerID: Int = CAN[50]
