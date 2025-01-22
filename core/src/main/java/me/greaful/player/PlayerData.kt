package me.greaful.player

class PlayerData {
    var username: String? = null
    var x: Float? = 0f
    var y: Float? = 0f
    var z: Float? = 0f
    var pitch: Float = 0f
    var yaw: Float = 0f
    var isMoving: Boolean = false
    var isRunning: Boolean = false
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
