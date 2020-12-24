package days

import util.animation.Animation
import util.animation.DrawPane24

class Day24 : Day(24) {
    val data = inputList.map { replaceDirs(it) }
    val set = mutableSetOf<Pair<Int, Int>>()
    val dirs = Direction.values().map { it.dir }

    override fun partOne(): Any {
        data.forEach { ot ->
            var pos = Pair(0, 0)
            val list = mutableListOf<Pair<Int,Int>>()
            ot.forEach {
                pos += when (it) {
                    'e' -> Direction.E.dir
                    'w' -> Direction.W.dir
                    'k' -> Direction.SE.dir
                    'm' -> Direction.SW.dir
                    'n' -> Direction.NW.dir
                    'p' -> Direction.NE.dir
                    else -> throw Exception("Unknown IT: $it")
                }
                list.add(pos)
            }
            if (set.contains(pos)) set.remove(pos) else set.add(pos)
            draw1(list, pos, set)
        }
        return set.size
    }

    override fun partTwo(): Any {
        var cState = mutableSetOf<Pair<Int, Int>>()
        cState.addAll(set)
        draw2(cState)
        for (i in 1..100) {
            val nState = mutableSetOf<Pair<Int, Int>>()
            val todos = mutableSetOf<Pair<Int, Int>>()
            cState.forEach { todos.add(it); dirs.forEach { d -> todos.add(it + d) } }
            todos.forEach {
                val adj = countAdj(it, cState)
                if (adj == 2 || (cState.contains(it) && adj == 1)) {
                    nState.add(it)
                }
            }
            cState = nState
            draw2(cState)
        }
        return cState.size
    }

    fun countAdj(p: Pair<Int, Int>, s: Set<Pair<Int, Int>>): Int =
        dirs.map { it + p }.filter { s.contains(it) }.count()

    fun replaceDirs(str: String) =
        str.replace("se", "k")
            .replace("sw", "m")
            .replace("nw", "n")
            .replace("ne", "p")

    operator fun Pair<Int, Int>.plus(o: Pair<Int, Int>): Pair<Int, Int> =
        Pair(this.first + o.first, this.second + o.second)

    fun Pair<Int, Int>.plus(o: Direction): Pair<Int, Int> = this + o.dir

    enum class Direction(val dir: Pair<Int, Int>) {
        E(Pair(2, 0)),
        W(Pair(-2, 0)),
        SE(Pair(1, 1)), // k
        SW(Pair(-1, 1)), // m
        NW(Pair(-1, -1)), // n
        NE(Pair(1, -1)) // p
    }

    var anim: Animation? = null
    val enableDraw = true

    fun draw1(way: List<Pair<Int, Int>>, end: Pair<Int, Int>, tiles: Set<Pair<Int, Int>>, sleep: Long = 50) {
        if (!enableDraw) return
        init()
        (anim!!.pane as DrawPane24).draw1(way, end, tiles, sleep)
    }

    fun draw2(tiles: Set<Pair<Int, Int>>, sleep: Long = 50) {
        if (!enableDraw) return
        init()
        (anim!!.pane as DrawPane24).draw2(tiles, sleep)
    }

    fun init() {
        if (!enableDraw) {
            return
        }
        if (anim == null) {
            anim = Animation(800, 800, 24)
            println("start animation?")
            readLine()
        }
    }
}