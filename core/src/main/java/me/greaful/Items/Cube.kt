package me.greaful.Items

import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder

class Cube(val l: Float, val w: Float, val h: Float) {
    lateinit var instance: ModelInstance
    lateinit var cubeModel: Model

    fun create() {
        val modelBuilder = ModelBuilder()
        cubeModel = modelBuilder.createBox(
            w, h, l,
            Material(),
            VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong()

        )
        instance = ModelInstance(cubeModel)

    }
}
