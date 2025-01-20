package me.greaful.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3
import net.mgsx.gltf.loaders.gltf.GLTFLoader
import net.mgsx.gltf.scene3d.scene.SceneAsset

class LoadGLTF(
    val path: String,
    val camera: PerspectiveCamera,
    val environment: Environment?,
    val x: Float,
    val y: Float,
    val z: Float
) {

    private lateinit var modelBatch: ModelBatch
    private lateinit var sceneAsset: SceneAsset
    private lateinit var modelInstance: ModelInstance

    fun create() {
        modelBatch = ModelBatch()
        sceneAsset = GLTFLoader().load(Gdx.files.internal(path))
        modelInstance = ModelInstance(sceneAsset.scene.model)
        modelInstance.transform.setToTranslation(Vector3(x, y, z))
    }

    fun load() {
        modelBatch.begin(camera)
        modelBatch.render(modelInstance, environment)
        modelBatch.end()
    }

    fun dispose() {
        modelBatch.dispose()
        sceneAsset.dispose()
    }
}
