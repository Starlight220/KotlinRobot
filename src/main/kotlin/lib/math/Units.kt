package lib.math

import kotlin.math.PI

inline class SIUnit<T>(val value : Double) : Comparable<SIUnit<T>>{
    override fun compareTo(other: SIUnit<T>): Int = value.compareTo(other.value)
}

object Meter
object Degree
object Volt

fun Double.meters() = SIUnit<Meter>(this)
fun Double.centimeters() = SIUnit<Meter>(100.0 * this)
fun Double.degrees() = SIUnit<Degree>(this)
fun Double.radians() = SIUnit<Degree>(180.0 / PI * this)
fun Double.volts() = SIUnit<Volt>(this)
