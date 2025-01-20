package me.greaful.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import me.greaful.Items.Cube
import me.greaful.system.InputHandler

class GameScreen : Screen {

    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private val aroundCube = Cube(2f, 2f, 2f)
    private lateinit var inputHandler: InputHandler


    override fun show() {

        environment = Environment().apply {
            set(ColorAttribute.createAmbient(0.4f, 0.4f, 0.4f, 1f)) // Ambient light
            add(DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f)) // Directional light
            add(DirectionalLight().set(1f, 1f, 1f, 1f, 0.8f, 0.2f))
        }

        camera = PerspectiveCamera(100f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(0f, 5f, 0f)
        camera.lookAt(0f, 0f, 0f)
        camera.near = 1f
        camera.far = 100f

        aroundCube.create()
        modelBatch = ModelBatch()

        camera.update()

    }

    override fun dispose() {
        modelBatch.dispose()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        inputHandler = InputHandler(camera)
        inputHandler.handleInputs()

        modelBatch.begin(camera)
        modelBatch.render(aroundCube.instance, environment)
        modelBatch.end()
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }
    // TODO
    override fun pause() {
    }
    override fun resume() {
    }
    override fun hide() {
    }
}
