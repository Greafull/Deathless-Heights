package me.greaful

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import me.greaful.screens.GameScreen
import me.greaful.system.input.InputConfiguration

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Main : Game() {
    override fun create() {
        setScreen(GameScreen())
        InputConfiguration

    }
}
