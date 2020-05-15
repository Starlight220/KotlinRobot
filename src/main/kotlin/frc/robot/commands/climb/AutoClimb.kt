package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.climbPower
import frc.robot.subsystems.Climber
import frc.robot.subsystems.Shooter

class AutoClimb(private val height : Int, private val followUp : Boolean = false,
                private val tolerance : Double = 0.0, private val bucketSize : Int = 5) : CommandBase(){
    private var isAutoFinished = false
    private val manualClimb : ManualClimb = ManualClimb()
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {}

    override fun execute() {
        if(isAutoFinished) Climber.height = height
        else manualClimb.execute()
    }

    private var count: Int = 0
    private fun isAutoFinished(){
        if(StrictMath.abs(height - Climber.height) < tolerance) count++ else count = 0
        isAutoFinished = count >= bucketSize
    }

    override fun isFinished(): Boolean{
        if(!isAutoFinished) isAutoFinished()
        if(isAutoFinished && !followUp) return true
        return false
    }


    override fun end(interrupted: Boolean) = -Climber

}