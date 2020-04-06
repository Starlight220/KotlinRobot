package frc.robot.commands

import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import java.io.IOException
import edu.wpi.first.wpilibj.DriverStation as DS


object Trajectories {
    private val map: Map<String, TrajectorySource> = mapOf(
            Pair("reverseShoot", JsonTrajectory("reverse")),
            Pair("forwardShoot", JsonTrajectory("fwd")),
            Pair("null", NullTrajectory)
    )

    operator fun get(name: String): Trajectory {
        val src = (map[name]) ?: NullTrajectory
        return src.getTrajectory()
    }

    interface TrajectorySource {
        fun getTrajectory(): Trajectory
    }

    object NullTrajectory : Trajectory(listOf()), TrajectorySource{
        override fun getTrajectory(): Trajectory {
            return this
        }
    }

    class JsonTrajectory(private val name: String) : TrajectorySource {
        override fun getTrajectory(): Trajectory {
            return try {
                val path = Filesystem.getDeployDirectory().toPath().resolve("output/$name.wpilib.json")
                TrajectoryUtil.fromPathweaverJson(path)
            } catch (e: IOException) {
                DS.reportError("trajectory $name generation failed : ${e.message}", false)
                return Trajectory(listOf())
            }
        }
    }
    class WpiTrajectory(private val states : List<Pose2d>) : TrajectorySource{
        companion object {
            private val config = TrajectoryConfig(10.0, 10.3)
        }
        override fun getTrajectory(): Trajectory {
            return TrajectoryGenerator.generateTrajectory(states, config)
        }

    }
}