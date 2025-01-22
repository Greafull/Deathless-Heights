package me.greaful.system.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import me.greaful.player.Keybind
import me.greaful.player.Player

class InputHandler(private val player: Player) : InputAdapter() {

    private val camera = player.camera!!
    private val inputConfig = InputConfiguration
    private val right = camera.up?.cpy()?.crs(camera.direction)?.nor() //right vector

    private fun handleMouseInput() {
        Gdx.input.isCursorCatched = true
        val sensitivity = 0.1
        val deltaX = -Gdx.input.deltaX * sensitivity
        val deltaY = Gdx.input.deltaY * sensitivity

        camera.rotateAround(camera.position, camera.up, deltaX.toFloat()) //yaw
        camera.direction.rotate(camera.up, deltaX.toFloat()) //adjust yaw direction
        camera.direction.rotate(right, deltaY.toFloat()) //adjust pitch
    }

    private fun handleKeyInput() {

        val speed = 5f * Gdx.graphics.deltaTime
        val direction = camera.direction
        val up = camera.up

        if (Gdx.input.isKeyPressed(inputConfig.preferences.getInteger(Keybind.FORWARD.name))) {
            camera.position.add(direction.x * speed, direction.y * speed, 0f)
            player.playerData.lastDirection = Keybind.FORWARD
        }
        if (Gdx.input.isKeyPressed(inputConfig.preferences.getInteger(Keybind.BACKWARD.name))) {
            camera.position.add(-direction.x * speed, -direction.y * speed, 0f)
            player.playerData.lastDirection = Keybind.BACKWARD
        }
        if (Gdx.input.isKeyPressed(inputConfig.preferences.getInteger(Keybind.LEFT.name))) {
            camera.position.add(right?.scl(speed))
            player.playerData.lastDirection = Keybind.LEFT
        }
        if (Gdx.input.isKeyPressed(inputConfig.preferences.getInteger(Keybind.RIGHT.name))) {
            camera.position.add(right?.scl(-speed))
            player.playerData.lastDirection = Keybind.RIGHT
        }
        if (Gdx.input.isKeyPressed(inputConfig.preferences.getInteger(Keybind.UP.name))) {
            camera.position.add(0f, 0f, up.z * speed)
            player.playerData.lastDirection = Keybind.UP
        }
        if (Gdx.input.isKeyPressed(inputConfig.preferences.getInteger(Keybind.DOWN.name))) {
            camera.position.add(0f, 0f, -up.z * speed)
            player.playerData.lastDirection = Keybind.DOWN
        }
    }

    fun handleInputs() {
        handleMouseInput()
        handleKeyInput()
        camera.update()
    }
}
