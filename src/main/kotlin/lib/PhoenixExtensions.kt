package lib

import com.ctre.phoenix.motorcontrol.can.VictorSPX as Victor
import com.ctre.phoenix.motorcontrol.can.TalonSRX as Talon

/**
 * Follows another TalonSRX, in the same direction.
 * @receiver the follower/slave controller
 * @param master the leader/master controller
 */
operator fun Talon.plus(master: Talon) : Talon {
    this.follow(master)
    return this
}
/**
 * Follows another TalonSRX, in the opposite direction.
 * @receiver the follower/slave controller
 * @param master the leader/master controller
 */
operator fun Talon.minus(master: Talon) : Talon {
    this.follow(master)
    return this
}

/**
 * Follows another VictorSPX, in the same direction.
 * @receiver the follower/slave controller
 * @param master the leader/master controller
 */
operator fun Victor.plus(master: Victor) : Victor {
    this.follow(master)
    return this
}

/**
 * Follows another VictorSPX, in the opposite direction.
 * @receiver the follower/slave controller
 * @param master the leader/master controller
 */
operator fun Victor.minus(master: Victor) : Victor {
    this.follow(master)
    return this
}

/**
 * Follows another TalonSRX, in the same direction.
 * @receiver the follower/slave controller
 * @param master the leader/master controller
 */
operator fun Victor.plus(master: Talon) : Victor {
    this.follow(master)
    return this
}

/**
 * Follows another TalonSRX, in the opposite direction.
 * @receiver the follower/slave controller
 * @param master the leader/master controller
 */
operator fun Victor.minus(master: Talon) : Victor {
    this.follow(master)
    return this
}