package lib.command

import edu.wpi.first.wpilibj2.command.SubsystemBase
import lib.interfaces.Initiable
import lib.interfaces.Releasable
import lib.test.Testable

abstract class XSubsystem : SubsystemBase(), Releasable, Testable, Initiable {

    override fun initTestVectors() {

    }

    override fun init() {

    }
}