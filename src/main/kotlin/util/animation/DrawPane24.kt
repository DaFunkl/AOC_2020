package util.animation

import javax.swing.JPanel
import java.awt.Color
import java.awt.Graphics

class DrawPane24 : JPanel() {
    var tiles: Set<Pair<Int, Int>> = setOf()
    var scale = 7
    var hScale = scale / 2
    var state = true
    var way: List<Pair<Int, Int>> = listOf()
    var end: Pair<Int, Int> = Pair(0,0)

    var offset = Pair(110, 70)


    public override fun paintComponent(g: Graphics) {
        g.color = Color.black
        g.fillRect(-1, -1, 2000, 2000)

        g.color = Color(69, 209, 78)
        for(p in tiles){
            var x = p.first + offset.first
            var y = p.second + offset.second
            g.fillOval(x * scale + hScale, y * scale + hScale, scale, scale) // Draw on g here e.g.
        }

        if(state){ // p1
            g.color = Color(124, 247, 235)
            for(p in way){
                var x = p.first + offset.first
                var y = p.second + offset.second
                g.fillOval(x * scale + hScale, y * scale + hScale, scale, scale) // Draw on g here e.g.
            }
            g.color = Color(56, 118, 252)
            var x = end.first + offset.first
            var y = end.second + offset.second
            g.fillOval(x * scale + hScale, y * scale + hScale, scale, scale) // Draw on g here e.g.
            g.color = Color(252, 167, 56)
            x = offset.first
            y = offset.second
            g.fillOval(x * scale + hScale, y * scale + hScale, scale, scale) // Draw on g here e.g.

        }
    }

    fun draw1(way: List<Pair<Int, Int>>, end: Pair<Int, Int>, tiles: Set<Pair<Int,Int>>, sleep: Long = 30) {
        state = true
        this.way = way
        this.end = end
        this.tiles = tiles
        revalidate()
        repaint()
        Thread.sleep(sleep)
    }

    fun draw2(tiles: Set<Pair<Int,Int>>, sleep: Long = 30) {
        state = false
        this.tiles = tiles
        revalidate()
        repaint()
        Thread.sleep(sleep)
    }
}