package frc.robot.commands.auto

import lib.trajectory.TrajectorySource.*

fun initTrajectories() {
    Trajectories.logAll {
        log(name = "reverseShoot", file = "reverse")
        log(name = "forwardShoot", file = "fwd")
    }
}

