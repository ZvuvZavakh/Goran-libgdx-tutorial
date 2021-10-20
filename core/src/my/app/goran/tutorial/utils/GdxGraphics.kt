package my.app.goran.tutorial.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

fun clearScreen(color: Color = Color.BLACK) {
    clearScreen(color.r, color.g, color.b, color.a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

fun clearScreen(red: Float = 1F, green: Float = 0F, blue: Float = 0F, alpha: Float = 1F) {
    Gdx.gl.glClearColor(red, green, blue, alpha)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

inline fun Batch.use(action: () -> Unit) {
    begin()
    action()
    end()
}

inline fun ShapeRenderer.use(action: () -> Unit) {
    begin(ShapeRenderer.ShapeType.Line)
    action()
    end()
}
