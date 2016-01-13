package Utils

import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11._

import scala.math._

object Vector {
    def draw(): Unit = {
        import Game.selected
        import Mouse.{getX, getY}
        if (selected != null && !selected.isCollide(getX.toFloat, getY.toFloat, 0)) {
            val dx = getX - selected.x
            val dy = getY - selected.y
            var force = sqrt(pow(dx, 2) + pow(dy, 2))
            val angle = atan2(dy / force, dx / force)
            if (force > 200)
                force = 200
            if (selected.player == 1)
                glColor3f(0.1f, 0.6f, 0.9f)
            else
                glColor3f(0.9f, 0.6f, 0.1f)
            glLineWidth(3f)
            glBegin(GL_POLYGON)
            val x = cos(angle) * selected.r + selected.x
            val y = sin(angle) * selected.r + selected.y
            force -= selected.r
            glVertex2d(x, y)
            glVertex2d(cos(angle - 0.15) * force + x, sin(angle - 0.15) * force + y)
            glVertex2d(cos(angle) * force * 0.8 + x, sin(angle) * force * 0.8 + y)
            glVertex2d(cos(angle + 0.15) * force + x, sin(angle + 0.15) * force + y)
            glVertex2d(x, y)
            glEnd

        }
    }
}