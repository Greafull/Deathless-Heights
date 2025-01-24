package me.greaful.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Cubemap
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import me.greaful.player.Player
import me.greaful.system.input.InputHandler
import me.greaful.system.LoadGLTF
import me.greaful.system.SkyboxShader

class GameScreen : ScreenAdapter() {

    private lateinit var player: Player
    private lateinit var camera: PerspectiveCamera
    private lateinit var environment: Environment
    private lateinit var inputHandler: InputHandler
    private lateinit var loadGLTF: LoadGLTF
    private var modelBatch: ModelBatch = ModelBatch()
    private lateinit var skyboxInstance: ModelInstance
    private val skyboxCubemap =
        Cubemap(
            Gdx.files.internal("textures/skybox/defaultSky/skybox/back.jpg"),
            Gdx.files.internal("textures/skybox/defaultSky/skybox/front.jpg"),
            Gdx.files.internal("textures/skybox/defaultSky/skybox/right.jpg"),
            Gdx.files.internal("textures/skybox/defaultSky/skybox/left.jpg"),
            Gdx.files.internal("textures/skybox/defaultSky/skybox/top.jpg"),
            Gdx.files.internal("textures/skybox/defaultSky/skybox/bottom.jpg"),
        )
    private lateinit var skyboxModel: Model
    private val shaderProgram = ShaderProgram(Gdx.files.internal("shaders/skybox.vert"), Gdx.files.internal("shaders/skybox.frag"))
    private lateinit var skyboxShader: SkyboxShader

    override fun show() {

        environment = Environment().apply {
            set(ColorAttribute.createAmbient(1f, 1f, 1f, 1f))
            add(DirectionalLight().set(1f, 1f, 1f, -1f, -1f, -1f))
            add(DirectionalLight().set(1f, 1f, 1f, 1f, 1f, 1f))

        }

        camera =
            PerspectiveCamera(100f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(0f, 5f, 0f)
        camera.lookAt(0f, 0f, 0f)
        camera.near = 0.1f
        camera.far = 100f
        player = Player(camera)

        skyboxShader = SkyboxShader(shaderProgram, camera, skyboxCubemap)

        loadGLTF = LoadGLTF("mesh/untitled.gltf", camera, 0f, 0f, 0f)
        createSkybox()
        loadGLTF.create()
        camera.update()
    }

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL)

        shaderProgram.bind()
        shaderProgram.setUniformMatrix("u_projTrans", camera.combined)
        shaderProgram.setUniformi("u_environmentCubemap", 0)
        skyboxCubemap.bind(0) // Bind cubemap texture to texture unit 0

        inputHandler = InputHandler(player)
        inputHandler.handleInputs()

        modelBatch.begin(camera)
        loadGLTF.renderGLTF(modelBatch, environment)
        modelBatch.render(skyboxInstance, skyboxShader)
        modelBatch.end()

        Gdx.gl.glDepthFunc(GL20.GL_LESS)
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    override fun dispose() {
        loadGLTF.dispose()
        modelBatch.dispose()
    }

    // TODO
    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    private fun createSkybox() {
        val builder = ModelBuilder()
        skyboxModel = builder.createBox(
            20f, 20f, 20f, // Large size to encompass the camera
            Material(),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
        )

        skyboxModel.meshes.first().let { mesh ->
            val vertices = FloatArray(mesh.numVertices * mesh.vertexSize / 4)
            mesh.getVertices(vertices)

            val indices = ShortArray(mesh.numIndices)
            mesh.getIndices(indices)

            val vertexSize = mesh.vertexSize / 4 // Vertex size in floats
            val normalOffset = mesh.getVertexAttribute(VertexAttributes.Usage.Normal).offset / 4

            // Negate the normals
            for (i in 0 until mesh.numVertices) {
                val normalIndex = i * vertexSize + normalOffset
                vertices[normalIndex] = -vertices[normalIndex]     // X
                vertices[normalIndex + 1] = -vertices[normalIndex + 1] // Y
                vertices[normalIndex + 2] = -vertices[normalIndex + 2] // Z
            }

            // Reverse the winding order of indices
            for (i in indices.indices step 3) {
                val tmp = indices[i]
                indices[i] = indices[i + 2]
                indices[i + 2] = tmp
            }

            // Update the mesh with modified data
            mesh.setVertices(vertices)
            mesh.setIndices(indices)


            skyboxInstance = ModelInstance(skyboxModel)
        }
    }
}
