package Elements

import org.lwjgl.opengl.GL11._

class Gate(val x: Float, val y: Float, val width: Float, val height: Float, val player: Int) {
    def draw(): Unit = {
        glBegin(GL_QUADS)
        if (player == 1)
            glColor3f(0f, 0.5f, 0.8f)

        else
            glColor3f(0.8f, 0.5f, 0f)
        glVertex2f(x, y)
        glVertex2f(x + width, y)
        glVertex2f(x + width, y + height)
        glVertex2f(x, y + height)
        glEnd

        glLineWidth(2f)
        glColor3f(1f, 1f, 1f)
        glBegin(GL_LINES)
        glVertex2f(x, y - 1)
        glVertex2f(x + width, y - 1)
        glEnd
        glBegin(GL_LINES)
        glVertex2f(x + width, y + height + 1)
        glVertex2f(x, y + height + 1)
        glEnd
    }
}