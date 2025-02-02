package me.greaful.system.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Preferences

object InputConfiguration {

    val preferences : Preferences = Gdx.app.getPreferences("KeybindConfig")

    init {
        try {
            preferences.get().getOrDefault("isSettingsSet", false)
            if (preferences.getBoolean("isSettingsSet") == false) {
                preferences.clear()
                preferences.putInteger(Keybind.FORWARD.name, Keys.W)
                preferences.putInteger(Keybind.BACKWARD.name, Keys.S)
                preferences.putInteger(Keybind.LEFT.name, Keys.A)
                preferences.putInteger(Keybind.RIGHT.name, Keys.D)
                preferences.putInteger(Keybind.UP.name, Keys.SPACE)
                preferences.putInteger(Keybind.DOWN.name, Keys.C)
                preferences.putBoolean("isSettingsSet", true)
                preferences.flush()
            }

        } catch (e: Exception) {
            Gdx.app.error("Exception", "error while creating preferences", e)
        }
    }

    fun updateKeybinds(newKey: Int, keybind: Keybind) {
        preferences.remove(keybind.name)
        preferences.putInteger(keybind.name, newKey)
        preferences.flush()
    }
}
