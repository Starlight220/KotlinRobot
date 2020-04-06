package frc.robot.subsystems
import lib.ports.CAN
import lib.ports.PCM
import lib.ports.navX

val rightMasterID = CAN[10]
val rightSlaveID = CAN[11]
val leftMasterID = CAN[12]
val leftSlaveID = CAN[13]
val gyroPort = navX.SPI

val flywheelID = CAN[20]

val intakeID = CAN[30]
val intakePistonClosed = PCM[0]
val intakePistonOpen = PCM[1]

val climbMasterID = CAN[40]
val climbSlaveID = CAN[41]

val flickerID = CAN[50]
val towerID = CAN[50]
