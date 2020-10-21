package frc.robot

import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d

operator fun Pose2d.component1() = x
operator fun Pose2d.component2() = y
operator fun Pose2d.component3(): Rotation2d? = rotation
