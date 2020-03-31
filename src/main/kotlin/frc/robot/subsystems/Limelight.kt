package frc.robot.subsystems

import edu.wpi.first.networktables.NetworkTableInstance

object Limelight {
    private val table = NetworkTableInstance.getDefault().getTable("limelight")
    private fun getEntry(data: String) = table.getEntry(data)

    var camMode: CamMode = CamMode.DRIVING
        set(mode) {
            field = mode
            getEntry("camMode").setNumber(mode.value)
        }
        get() = CamMode.fromNumber((getEntry("camMode").getNumber(1).toInt()))
    var ledMode: LedMode = LedMode.DEFAULT
        set(mode) {
            field = mode
            getEntry("ledMode").setNumber(mode.value)
        }
        get() = LedMode.fromNumber((getEntry("ledMode").getNumber(1).toInt()))
    var pipline: Int = 0
        set(value) {
            field = value
            getEntry("pipeline").setNumber(value)
        }
        get() = getEntry("pipeline").getNumber(0).toInt()

    val tx: Double
        get() = getEntry("tx").getDouble(0.0)
    val tv: Boolean
        get() = getEntry("tv").getBoolean(false)
    val ty: Double
        get() = getEntry("ty").getDouble(0.0)
    val ta: Double
        get() = getEntry("ta").getDouble(0.0)
    val tshort: Double
        get() = getEntry("tshort").getDouble(0.0)
    val tlong: Double
        get() = getEntry("tlong").getDouble(0.0)
    val thor: Double
        get() = getEntry("thor").getDouble(0.0)
    val tvert: Double
        get() = getEntry("tvert").getDouble(0.0)
    /*
           Values:
               Getters:
                   tv - Whether the limelight has any valid targets (0 or 1)
                   tx - Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
                   ty - Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
                   ta - Target Area (0% of image to 100% of image)
                   tshort - Sidelength of shortest side of the fitted bounding box (pixels)
                   tlong - Sidelength of longest side of the fitted bounding box (pixels)
                   thor - Horizontal sidelength of the rough bounding box (0 - 320 pixels)
                   tvert - Vertical sidelength of the rough bounding box (0 - 320 pixels)
               Setters:
                   LED mode : 1 (off), 2 (blink), 3 (on)
                   Cam Mode : 0 Vision, 1 Driving
       */


//    double tshort;
//    double tlong;
//    double thor;
//    double tvert;
//    double camMode, ledMode, pipeline;


    enum class CamMode(val value: Int) {
        VISION(0), DRIVING(1);

        companion object {
            fun fromNumber(number: Int): CamMode = when (number) {
                0 -> VISION
                1 -> DRIVING
                else -> throw IllegalArgumentException("No CamMode for value $number")
            }
        }
    }

    enum class LedMode(val value: Int) {
        DEFAULT(0), OFF(1), BLINK(2), ON(3);

        companion object {
            fun fromNumber(number: Int): LedMode = when (number) {
                0 -> DEFAULT
                1 -> OFF
                2 -> BLINK
                3 -> ON
                else -> throw IllegalArgumentException("No CamMode for value $number")
            }
        }
    }
}
