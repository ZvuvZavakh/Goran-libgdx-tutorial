package my.app.goran.tutorial.utils

import com.badlogic.gdx.utils.Logger

inline fun <reified T : Any> getLogger(): Logger {
    return Logger(T::class.java.simpleName, Logger.DEBUG)
}