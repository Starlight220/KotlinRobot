package utils

import com.ctre.phoenix.motorcontrol.can.VictorSPX as Victor
import com.ctre.phoenix.motorcontrol.can.TalonSRX as Talon


operator fun Talon.plus(master: Talon) : Talon {
    this.follow(master)
    return this
}
operator fun Talon.minus(master: Talon) : Talon {
    this.follow(master)
    return this
}
operator fun Victor.plus(master: Victor) : Victor {
    this.follow(master)
    return this
}
operator fun Victor.minus(master: Victor) : Victor {
    this.follow(master)
    return this
}
operator fun Victor.plus(master: Talon) : Victor {
    this.follow(master)
    return this
}
operator fun Victor.minus(master: Talon) : Victor {
    this.follow(master)
    return this
}