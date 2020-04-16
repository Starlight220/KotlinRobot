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
            "reverseShoot" to   TrajectorySource.JsonTrajectory("reverse"),
            "forwardShoot" to   TrajectorySource.JsonTrajectory("fwd"),
            "null"         to   TrajectorySource.NullTrajectory
    )

    operator fun get(name: String): Trajectory {
        val src = (map[name]) ?: TrajectorySource.NullTrajectory
        return src.getTrajectory()
    }

    sealed class TrajectorySource {
        abstract fun getTrajectory(): Trajectory

        object NullTrajectory : TrajectorySource() {
            override fun getTrajectory(): Trajectory {
                return Trajectory(listOf())
            }
        }
        class JsonTrajectory(private val name: String) : TrajectorySource() {
            override fun getTrajectory(): Trajectory {
                return try {
                    val path = Filesystem.getDeployDirectory()
                            .toPath()
                            .resolve("output/$name.wpilib.json")

                    TrajectoryUtil.fromPathweaverJson(path)
                } catch (e: IOException) {
                    DS.reportError("file trajectory $name generation failed : ${e.message}", true)
                    return NullTrajectory.getTrajectory()
                }
            }
        }
        class WpiTrajectory(private val poses : List<Pose2d>) : TrajectorySource() {
            companion object {
                private val config = TrajectoryConfig(10.0, 10.3)
            }
            override fun getTrajectory(): Trajectory {
                return TrajectoryGenerator.generateTrajectory(poses, config)
            }
        }
    }
}