package me.greaful.system

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Cubemap
import com.badlogic.gdx.graphics.g3d.Renderable
import com.badlogic.gdx.graphics.g3d.Shader
import com.badlogic.gdx.graphics.g3d.utils.RenderContext
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class SkyboxShader(
    private val shaderProgram: ShaderProgram,
    private var camera: Camera?,
    private val skyboxCubemap: Cubemap
) : Shader {

    override fun dispose() {
        shaderProgram.dispose()
    }

    override fun end() {
    }

    override fun canRender(instance: Renderable?): Boolean {
        return true
    }

    override fun render(renderable: Renderable?) {
        shaderProgram.bind()
        shaderProgram.setUniformMatrix("u_projTrans", camera?.combined)
        shaderProgram.setUniformi("u_environmentCubemap", 0)
        skyboxCubemap.bind(0) // Bind cubemap texture to texture unit 0
        renderable?.meshPart?.render(shaderProgram)
    }

    override fun init() {
  //not().
        TODO("Not needed")
    }

    override fun begin(camera: Camera?, context: RenderContext?) {
    }

    override fun compareTo(other: Shader?): Int {
        return 0
    }
}
