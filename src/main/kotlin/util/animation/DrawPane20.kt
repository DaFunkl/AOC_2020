package util.animation

import javax.swing.JPanel
import java.awt.Color
import java.awt.Graphics

class DrawPane20 : JPanel() {
    var grid: List<String>? = null
    val scale1 = 5
    val scale2 = 6
    var scale = scale1
    var hScale = scale / 2

    public override fun paintComponent(g: Graphics) {
        if (grid == null) {
            return
        }
        for (y in grid!!.indices) {
            for (x in grid!![y].indices) {
                when (grid!![y][x]) {
                    '.' -> g.color = Color(29, 117, 165)
                    '#' -> g.color = Color(29, 165, 165)
                    'X' -> g.color = Color(0, 0, 0)
                    'O' -> g.color = Color(240, 75, 175)
                    'M' -> g.color = Color(170, 240, 100)
                    else -> g.color = Color.orange
                }
                g.fillRect(x * scale + hScale, y * scale + hScale, scale, scale) // Draw on g here e.g.
            }
        }
    }

    fun drawGrid(grid: List<String>, sleep: Long = 30) {
        scale = if (grid.size > 100) scale1 else scale2
        hScale = scale / 2
        this.grid = grid
        revalidate()
        repaint()
        Thread.sleep(sleep)
    }
}