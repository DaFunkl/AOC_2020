package util.animation

import javax.swing.JPanel
import java.awt.Color
import java.awt.Graphics
import java.util.ArrayList

class DrawPane11 : JPanel() {
    var grid: ArrayList<CharArray>? = null
    val scale = 8
    val hScale = scale / 2

    public override fun paintComponent(g: Graphics) {
        if (grid == null) {
            return
        }
        for (y in grid!!.indices) {
            for (x in grid!![y].indices) {
                when (grid!![y][x]) {
                    _FREE -> g.color = Color(15, 62, 87)
                    _OCCU -> g.color = Color(53, 90, 83)
                    _EMPT -> g.color = Color(99, 128, 146)
                    else -> g.color = Color.orange
                }
                g.fillRect(x * scale + hScale, y * scale + hScale, scale, scale) // Draw on g here e.g.
            }
        }
    }

    fun drawGrid(grid: ArrayList<CharArray>?) {
        this.grid = grid
        revalidate()
        repaint()
        Thread.sleep(75)
    }

    companion object {
        const val _FREE = '.'
        const val _OCCU = '#'
        const val _EMPT = 'L'
    }
}