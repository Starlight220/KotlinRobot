package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.SelectCommand
import edu.wpi.first.wpilibj2.command.button.Button
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.robot.commands.climb.AutoClimb
import frc.robot.commands.climb.ManualClimb
import frc.robot.commands.auto.getShootProcess
import frc.robot.subsystems.heightTarget
import java.util.function.Supplier


private val driverJoystick : Joystick = Joystick(0)
private val armJoystick : Joystick = Joystick(1)
val climbMode : String
    get() {
        return ""
    }


val shoot : Boolean
    get() = armJoystick.getRawButton(1)
val intakeActive : Boolean
    get() = armJoystick.getRawButton(2)
val intakeOpen : Boolean
    get() = armJoystick.getRawButton(3)
val intakeClosed : Boolean
    get() = armJoystick.getRawButton(4)
//val intakeOpen : Boolean
//    get() = armJoystick.getRawButton(5)
//val intakeOpen : Boolean
//    get() = armJoystick.getRawButton(6)
private val shootProcess : Button = JoystickButton(armJoystick, 7)
        .toggleWhenPressed(getShootProcess { shoot })
//val intakeOpen : Boolean
//    get() = armJoystick.getRawButton(8)
//val intakeOpen : Boolean
//    get() = armJoystick.getRawButton(9)
//val intakeOpen : Boolean
//    get() = armJoystick.getRawButton(10)
//val intakeOpen : Boolean
//    get() = armJoystick.getRawButton(11)
val ejectTransport : Boolean
    get() = armJoystick.getRawButton(12)
//*************************************************
//val xSpeedAxis : Double
//    get() = armJoystick.getRawAxis(1)
//val xSpeedAxis : Double
//    get() = armJoystick.getRawAxis(2)
//val xSpeedAxis : Double
//    get() = armJoystick.getRawAxis(3)
val climbPower : Double
    get() = armJoystick.getRawAxis(4)
//val xSpeedAxis : Double
//    get() = armJoystick.getRawAxis(5)

//---------------------------------------------------
//val shoot : Boolean
//    get() = driverJoystick.getRawButton(1)
//val intakeActive : Boolean
//    get() = driverJoystick.getRawButton(2)
//val intakeOpen : Boolean
//    get() = driverJoystick.getRawButton(3)
//val intakeClosed : Boolean
//    get() = driverJoystick.getRawButton(4)
//val intakeOpen : Boolean
//    get() = driverJoystick.getRawButton(5)
//val intakeOpen : Boolean
//    get() = driverJoystick.getRawButton(6)
private val startClimbButton : Button = JoystickButton(driverJoystick, 7)
        .toggleWhenPressed(SelectCommand(mapOf(
                Pair("manual",ManualClimb()),
                Pair("auto", AutoClimb(heightTarget, true))),
                Supplier {climbMode}))
//val intakeOpen : Boolean
//    get() = driverJoystick.getRawButton(8)
//val intakeOpen : Boolean
//    get() = driverJoystick.getRawButton(9)
//val intakeOpen : Boolean
//    get() = driverJoystick.getRawButton(10)
//*************************************************
val xSpeedAxis : Double
    get() = driverJoystick.getRawAxis(1)
//val xSpeedAxis : Double
//    get() = driverJoystick.getRawAxis(2)
val zRotateAxis : Double
    get() = driverJoystick.getRawAxis(3)
//val xSpeedAxis : Double
//    get() = driverJoystick.getRawAxis(4)
//val xSpeedAxis : Double
//    get() = driverJoystick.getRawAxis(5)






