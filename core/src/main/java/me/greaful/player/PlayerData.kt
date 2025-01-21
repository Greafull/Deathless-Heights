package me.greaful.player

class PlayerData {
    private var username: String? = null
    private var x: Float? = 0f
    private var y: Float? = 0f
    private var z: Float? = 0f
    private var pitch: Float = 0f
    private var yaw: Float = 0f
    private var isMoving: Boolean = false
    private var isRunning: Boolean = false
    var lastDirection: Keybind = Keybind.FORWARD

    fun copy() : PlayerData {
        val copy = PlayerData()
        copy.username = username
        copy.x = x
        copy.y = y
        copy.z = z
        copy.pitch = pitch
        copy.yaw = yaw
        copy.isMoving = isMoving
        copy.isRunning = isRunning
        return copy
    }
}
