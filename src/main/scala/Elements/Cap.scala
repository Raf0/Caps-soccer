package Elements

import Utils.Game

import org.lwjgl.opengl.GL11._

import scala.math._

class Cap(var x: Float, var y: Float, var r: Float, private var speed: Double, private var angle: Double,
          val mass: Double, val player: Int) {
    def move {
        if (speed > 0)
            speed *= 0.985

        if (speed < 0.15) {
            speed = 0
        }
        x += (speed * cos(angle)).toFloat
        y += (speed * sin(angle)).toFloat
        isGoal
        checkBorders
    }

    def back: Unit = {
        x -= (speed * cos(angle)).toFloat / 10
        y -= (speed * sin(angle)).toFloat / 10
    }


    def collision(sec: Cap): Unit = {
        while (isCollide(sec.x, sec.y, sec.r) && (speed != 0 || sec.speed != 0)) {
            back
            sec.back
        }
        val k = 0.9
        val m = mass / sec.mass
        val sinFI = (sec.x - x) / sqrt(pow(sec.x - x, 2) + pow(y - sec.y, 2))
        val cosFI = (y - sec.y) / sqrt(pow(sec.x - x, 2) + pow(y - sec.y, 2))
        val sin1mFI = sin(angle) * cosFI - sinFI * cos(angle)
        val cos1mFI = sin(angle) * sinFI + cosFI * cos(angle)
        val sin2mFI = sin(sec.angle) * cosFI - sinFI * cos(sec.angle)
        val cos2mFI = sin(sec.angle) * sinFI + cosFI * cos(sec.angle)
        val W1D = speed * cos1mFI
        val W1E = speed * (m - k) * sin1mFI / (m + 1) + sec.speed * (k + 1) * sin2mFI / (m + 1)
        val W2D = sec.speed * cos2mFI
        val W2E = speed * m * (k + 1) * sin1mFI / (m + 1) + sec.speed * (1 - k * m) * sin2mFI / (m + 1)
        val W1x = W1D * cosFI - W1E * sinFI
        val W1y = W1D * sinFI + W1E * cosFI
        val W2x = W2D * cosFI - W2E * sinFI
        val W2y = W2D * sinFI + W2E * cosFI
        speed = sqrt(W1x * W1x + W1y * W1y)
        angle = atan2(W1y / speed, W1x / speed)
        sec.speed = sqrt(W2x * W2x + W2y * W2y)
        sec.angle = atan2(W2y / sec.speed, W2x / sec.speed)
    }

    def isGoal(): Unit = {
        if (player == 0)
            for (gate <- Game.gates) {
                if (min(abs(x - gate.x), abs(x - gate.x - gate.width)) < r
                    && y + r > gate.y && y - r < gate.y + gate.height) {
                    Game.won = max(1, (1 + gate.player) % 3)
                    speed = 0
                    x = Game.width / 2
                    y = Game.height / 2
                    r = 0
                }
            }
    }

    def isCollide(x2: Float, y2: Float, r2: Float): Boolean = {
        return distance(x2, y2) <= r + r2
    }

    def distance(x2: Float, y2: Float): Float = {
        return sqrt(pow(x - x2, 2) + pow(y - y2, 2)).toFloat
    }

    def addSpeedMouse(mouseX: Double, mouseY: Double): Unit = {
        speed = min(distance(mouseX.toFloat, mouseY.toFloat) / 8, 25)
        angle = atan2((y - mouseY) / speed, (x - mouseX) / speed)

    }

    def checkBorders(): Unit = {
        if (x + r > Cap.width - Cap.border) {
            while (x + r > Cap.width - Cap.border)
                back
            angle = (3.14159 - angle) % (2 * 3.15169)
        }
        if (x - r < Cap.border) {
            while (x - r < Cap.border)
                back
            angle = (-3.14159 - angle) % (2 * 3.15169)
        }
        if (y + r > Cap.height - Cap.border) {
            while (y + r > Cap.height - Cap.border)
                back
            angle = (3.14159 * 2 - angle) % (2 * 3.15169)
        }
        if (y - r < Cap.border) {
            while (y - r < Cap.border)
                back
            angle = (-3.14159 * 2 - angle) % (2 * 3.15169)
        }

    }

    def draw = {
        player match {
            case 1 => glColor3f(0f, 0f, 1f)
            case 2 => glColor3f(1f, 0f, 0f)
            case _ => glColor3f(0.25f, 0.1f, 0.1f)
        }
        glBegin(GL_POLYGON)
        for (i <- 0 to 100) {
            val theta: Double = 2.0d * 3.1415926d * i / 100
            val _x = (0.95 * r * cos(theta)).toFloat
            val _y = (0.95 * r * sin(theta)).toFloat
            glVertex2f(x + _x, y + _y)
        }
        glEnd

        glColor3f(0f, 0f, 0f)
        glBegin(GL_POLYGON)
        for (i <- 0 to 100) {
            val theta: Double = 2.0d * 3.1415926d * i / 100
            val _x = (1 * r * cos(theta)).toFloat
            val _y = (1 * r * sin(theta)).toFloat
            glVertex2f(x + _x, y + _y)
        }
        glEnd
    }
}

object Cap {
    var width = 0
    var height = 0
    var border = 0

    def apply(x: Float, y: Float, player: Int) = {
        new Cap(x, y, 40, 0, 0, 1, player)
    }
}

