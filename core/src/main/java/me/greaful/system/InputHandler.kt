package me.greaful.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.PerspectiveCamera

class InputHandler(private val camera: PerspectiveCamera) {

    private val right = camera.up.cpy().crs(camera.direction).nor() //right vector

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

        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.add(direction.x * speed, direction.y * speed, 0f) //forward
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.add(-direction.x * speed, -direction.y * speed, 0f) //backward
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.add(right.scl(speed)) //left
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.add(right.scl(-speed)) //right
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) camera.position.add(0f, 0f, up.z * speed) //up
        if (Gdx.input.isKeyPressed(Input.Keys.C)) camera.position.add(0f, 0f, -up.z * speed) //down
    }

    fun handleInputs() {
        handleMouseInput()
        handleKeyInput()
        camera.update()
    }
}
