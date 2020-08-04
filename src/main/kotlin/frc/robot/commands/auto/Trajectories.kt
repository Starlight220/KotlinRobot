package frc.robot.commands.auto

import frc.excalibur.lib.trajectory.TrajectorySource

object Trajectories {
    init {
        initTrajectories()
    }

    fun initTrajectories() {
        TrajectorySource.Trajectories.logAll {
            log(name = "reverseShoot", file = "reverse")
            log(name = "forwardShoot", file = "fwd")
        }
    }
}
