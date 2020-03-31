package frc.robot.commands

import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import java.io.IOException
import java.nio.file.Path
import edu.wpi.first.wpilibj.DriverStation as DS


object Trajectories{
    private val map : Map<String, String> = mapOf(
            Pair("reverseShoot","reverseShoot.json"),
            Pair("forwardShoot","forwardShoot.json")
    )

    operator fun get(name : String) : Trajectory{
        return try {
            TrajectoryUtil.fromPathweaverJson(Path.of(map[name]))
        }catch (e : IOException){
            DS.reportError("trajectory $name generation failed : ${e.message}", false)
            Trajectory(listOf())
        }
    }
}