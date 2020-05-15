package frc.robot.commands.auto

import frc.excalibur.lib.trajectory.TrajectorySource.*

fun initTrajectories() {
    Trajectories.logAll {
        log(name = "reverseShoot", file = "reverse")
        log(name = "forwardShoot", file = "fwd")
    }
}

