package Utils

import org.lwjgl.opengl.GL11._

import scala.math._

object Interface {
    var player1 = 0
    var player2 = 0

    def draw(): Unit = {

        glColor3f(1f, 1f, 1f)
        glLineWidth(2f)
        glBegin(GL_LINE_LOOP)
        glVertex2f(Game.border, Game.border)
        glVertex2f(Game.border, Game.height - Game.border)
        glVertex2f(Game.width - Game.border, Game.height - Game.border)
        glVertex2f(Game.width - Game.border, Game.border)
        glEnd

        glBegin(GL_LINES)
        glVertex2f(Game.width / 2, Game.border)
        glVertex2f(Game.width / 2, Game.height - Game.border)
        glEnd
        //Circle
        glBegin(GL_LINE_LOOP)
        for (i <- 0 to 100) {
            val theta: Double = 2.0d * 3.1415926d * i / 100
            val _x = (0.95 * 125 * cos(theta)).toFloat
            val _y = (0.95 * 125 * sin(theta)).toFloat
            glVertex2f(Game.width/2 + _x, Game.height/2 + _y)
        }
        glEnd
        //Dot
        glBegin(GL_POLYGON)
        for (i <- 0 to 100) {
            val theta: Double = 2.0d * 3.1415926d * i / 100
            val _x = (0.95 * 15 * cos(theta)).toFloat
            val _y = (0.95 * 15 * sin(theta)).toFloat
            glVertex2f(Game.width/2 + _x, Game.height/2 + _y)
        }
        glEnd

        //Whos turn
        if (Game.turn == 1)
            glColor3f(0f, 0f, 1f)
        else
            glColor3f(1f, 0f, 0f)
        glBegin(GL_QUADS)
        glVertex2f(Game.width / 2 - 100, Game.height)
        glVertex2f(Game.width / 2 - 100, Game.height - 20)
        glVertex2f(Game.width / 2 + 100, Game.height - 20)
        glVertex2f(Game.width / 2 + 100, Game.height)
        glEnd

        //Points
        glColor3f(0.3f, 0f, 1f)
        for (i <- 1 to player1) {
            glBegin(GL_QUADS)
            glVertex2f(50 + 50 * i, Game.height - Game.border)
            glVertex2f(75 + 50 * i, Game.height - Game.border)
            glVertex2f(75 + 50 * i, Game.height - Game.border + 25)
            glVertex2f(50 + 50 * i, Game.height - Game.border + 25)
            glEnd
        }
        glColor3f(1f, 0.3f, 0f)
        for (i <- 1 to player2) {
            glBegin(GL_QUADS)
            glVertex2f(Game.width - 50 - 50 * i, Game.height - Game.border)
            glVertex2f(Game.width - 75 - 50 * i, Game.height - Game.border)
            glVertex2f(Game.width - 75 - 50 * i, Game.height - Game.border + 25)
            glVertex2f(Game.width - 50 - 50 * i, Game.height - Game.border + 25)
            glEnd
        }

        glColor3f(0.15f, 0.75f, 0.05f)
        glBegin(GL_POLYGON)
        glVertex2f(Game.border, Game.border)
        glVertex2f(Game.border, Game.height - Game.border)
        glVertex2f(Game.width - Game.border, Game.height - Game.border)
        glVertex2f(Game.width - Game.border, Game.border)
        glEnd
    }
}
