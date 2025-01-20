package me.greaful.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import me.greaful.system.InputHandler
import me.greaful.system.LoadGLTF

class GameScreen : ScreenAdapter() {

    private lateinit var camera: PerspectiveCamera
    private lateinit var environment: Environment
    private lateinit var inputHandler: InputHandler
    private lateinit var loadGLTF: LoadGLTF


    override fun show() {

        environment = Environment().apply {
            set(ColorAttribute.createAmbient(1f, 1f, 1f, 1f)) // Ambient light
            set(ColorAttribute.createFog(1f, 1f, 1f, 1f))
        }

        camera = PerspectiveCamera(100f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(0f, 5f, 0f)
        camera.lookAt(0f, 0f, 0f)
        camera.near = 1f
        camera.far = 100f

        loadGLTF = LoadGLTF("untitled.gltf", camera, environment, 0f,0f, 0f)

        loadGLTF.create()

        for (material in loadGLTF.modelInstance.materials) {
            material.set(ColorAttribute.createEmissive(Color.ORANGE)) // Full ambient light
        }

        camera.update()

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        inputHandler = InputHandler(camera)
        inputHandler.handleInputs()

        loadGLTF.load()
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    override fun dispose() {
        loadGLTF.dispose()
    }

    // TODO
    override fun pause() {
    }
    override fun resume() {
    }
    override fun hide() {
    }
}
