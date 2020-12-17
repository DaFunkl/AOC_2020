package days

class Day17 : Day(17) {

    private val size = 21
    private val zero = size / 2
    private var grid1 = Array(size) { Array(size) { BooleanArray(size) } }
    private var grid2 = Array(size) { Array(size) { Array(size) { BooleanArray(size) } } }

    override fun partOne(): Any {
        parse()
        val runs = 6
        for (i in 1..runs) {
            val tempGrid = Array(size) { Array(size) { BooleanArray(size) } }
            for ((x, _) in grid1.withIndex()) {
                for ((y, _) in grid1[x].withIndex()) {
                    for ((z, c) in grid1[x][y].withIndex()) {
                        val activity = countActivity1(x, y, z)
                        if (c) tempGrid[x][y][z] = activity == 2 || activity == 3
                        else tempGrid[x][y][z] = activity == 3
                    }
                }
            }
            grid1 = tempGrid
        }
        return countActive1()
    }

    private fun countActive1(): Int = grid1.sumBy { it.sumBy { it.count { it } } }


    private fun countActivity1(x: Int, y: Int, z: Int): Int {
        var count = 0
        for (xx in (x - 1)..(x + 1)) {
            if (xx < 0 || xx >= size) continue
            for (yy in (y - 1)..(y + 1)) {
                if (yy < 0 || yy >= size) continue
                for (zz in (z - 1)..(z + 1)) {
                    if (zz < 0 || zz >= size) continue
                    if (xx == x && yy == y && zz == z) continue
                    if (grid1[xx][yy][zz]) count++
                }
            }
        }
        return count
    }

    override fun partTwo(): Any {
        parse()
        val runs = 6
        for (i in 1..runs) {
            val tempGrid = Array(size) { Array(size) { Array(size) { BooleanArray(size) } } }
            for ((x, _) in grid2.withIndex()) {
                for ((y, _) in grid2[x].withIndex()) {
                    for ((z, _) in grid2[x][y].withIndex()) {
                        for ((w, c) in grid2[x][y][z].withIndex()) {
                            val activity = countActivity2(x, y, z, w)
                            if (c) tempGrid[x][y][z][w] = activity == 2 || activity == 3
                            else tempGrid[x][y][z][w] = activity == 3
                        }
                    }
                }
            }
            grid2 = tempGrid
        }
        return countActive2()
    }

    private fun countActive2(): Int = grid2.sumBy { it.sumBy { it.sumBy { it.count { it } } } }


    private fun countActivity2(x: Int, y: Int, z: Int, w: Int): Int {
        var count = 0
        for (xx in (x - 1)..(x + 1)) {
            if (xx < 0 || xx >= size) continue
            for (yy in (y - 1)..(y + 1)) {
                if (yy < 0 || yy >= size) continue
                for (zz in (z - 1)..(z + 1)) {
                    if (zz < 0 || zz >= size) continue
                    for (ww in (w - 1)..(w + 1)) {
                        if (ww < 0 || ww >= size) continue
                        if (xx == x && yy == y && zz == z && ww == w) continue
                        if (grid2[xx][yy][zz][ww]) count++
                    }
                }
            }
        }
        return count
    }

    private fun parse() {
        val z = 0
        val w = 0
        val ihx = inputList.size / 2
        val ihy = inputList[0].length / 2
        for ((x, r) in inputList.withIndex()) {
            for ((y, c) in r.withIndex()) {
                grid1[x + zero - ihx][y + zero - ihy][z + zero] = c == '#'
                grid2[x + zero - ihx][y + zero - ihy][z + zero][w + zero] = c == '#'
            }
        }
    }
}
