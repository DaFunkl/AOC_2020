package util.animation

import java.awt.Color
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.JPanel

class Animation : JFrame {
    var pane: JPanel? = null

    constructor(w: Int, h: Int, day: Int) : super("AOC_20") {
        if (day == 11) {
            pane = DrawPane11()
        }
        setSize(w, h)
        contentPane = pane
        val dimension = Toolkit.getDefaultToolkit().screenSize
        val x = ((dimension.getWidth() - width) / 2).toInt()
        val y = ((dimension.getHeight() - height) / 2).toInt()
        setLocation(x, y)
        background = Color.black
        title = "AOC_20"
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}