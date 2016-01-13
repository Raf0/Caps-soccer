package Utils

import Elements.{Cap, Gate}
import org.lwjgl.input._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{Display, DisplayMode, PixelFormat}

import scala.math._

object Game {

    val GAME_TITLE = "Kapsle"
    val FRAMERATE = 60
    val width = 1300
    Cap.width = width
    val height = 700
    Cap.height = height
    val border = 50
    Cap.border = 50
    var turn = 1
    var won = 0

    val gates = List(new Gate(0, 250, border + 1, height - 500, 1), new Gate(width - border - 1, 250, 2 + border, height - 500, 2))

    var caps: List[Cap] = null
    setCaps

    var selected: Cap = null


    var finished = false
    var angle = 0.0f
    var rotation = 0.0f


    def setCaps(): Unit = {
        caps = List(
            Cap(200, 175, 1), Cap(300, 275, 1), Cap(400, height / 2, 1),
            Cap(300, height - 275, 1), Cap(200, height - 175, 1),
            Cap(width - 200, 175, 2), Cap(width - 300, 275, 2), Cap(width - 400, height / 2, 2),
            Cap(width - 300, height - 275, 2), Cap(width - 200, height - 175, 2),
            Cap(width / 2, height / 2, 0)
        )
    }

    def init() {

        Display.setTitle(GAME_TITLE)
        Display.setFullscreen(false)
        Display.setVSyncEnabled(true)
        Display.setDisplayMode(new DisplayMode(width, height))
        Display.create(new PixelFormat(8, 8, 0, 8))

        glEnable(GL_TEXTURE_2D)
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_BLEND)
        glEnable(GL_MULT)
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST)

        glViewport(0, 0, width, height)
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity
        glOrtho(0, width, 0, height, 1, -1)
        glMatrixMode(GL_MODELVIEW)
    }


    def cleanup() {
        Display.destroy
    }

    def run() {
        while (!finished) {
            Display.update

            logic
            render

            Display.sync(FRAMERATE)
        }
    }

    def logic() {
        import Keyboard._
        import Mouse._

        if (isKeyDown(KEY_ESCAPE))
            finished = true
        if (Display.isCloseRequested)
            finished = true

        if (won == 1) {
            setCaps
            turn = 2
            won = 0
            Interface.player1 += 1
        } else if (won == 2) {
            setCaps
            turn = 1
            won = 0
            Interface.player2 += 1
        }
        if (Interface.player1 < 3 && Interface.player2 < 3) {
            if (Mouse.isButtonDown(0)) {
                if (selected == null)
                    for (cap <- caps) {
                        if (cap.isCollide(getX.toFloat, getY.toFloat, 0) && cap.player == turn) {
                            selected = cap
                        }
                    }
            }
            else {
                if (selected != null) {
                    if (!selected.isCollide(getX.toFloat, getY.toFloat, 0)) {
                        selected.addSpeedMouse(getX, getY)
                        turn = max((turn + 1) % 3, 1)
                    }
                    selected = null
                }

            }

            for (cap <- caps) {
                //print("Move ")
                //println(cap)
                cap.move
                for (cap2 <- caps)
                    if (cap != cap2 && cap.isCollide(cap2.x, cap2.y, cap2.r)) {
                        cap.collision(cap2)
                        cap.move
                        cap2.move
                    }

            }
        } else {
            if (Mouse.isButtonDown(0)) {
                Interface.player1 = 0
                Interface.player2 = 0
            }
        }
    }

    def render() {
        if (Interface.player1 >= 3) {
            glClearColor(0f, 0f, 1f, 1f)
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        } else if (Interface.player2 >= 3) {
            glClearColor(1f, 0f, 0f, 1f)
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        } else {

            glClearColor(0.1f, 0.45f, 0.05f, 1f)
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        }
        Vector.draw

        for (cap <- caps) {
            cap.draw
        }
        for (gate <- gates) {
            gate.draw
        }
        Interface.draw
    }

}
