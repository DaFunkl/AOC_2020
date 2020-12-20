package days

class Day20 : Day(20) {
    enum class Direction { UP, DOWN, LEFT, RIGHT }
    data class Rot(val edge: String, val direction: Direction)
    data class Fit(val p1: Int, val dir1: Direction, val p2: Int, val dir2: Direction)

    val tileSize = 10
    val tiles = mutableMapOf<Int, List<String>>()
    val tileFit: MutableMap<Int, Int> = mutableMapOf()
    val edges: MutableMap<Int, List<Rot>> = mutableMapOf()
    val puzzle: MutableList<Fit> = mutableListOf()
    var start = -1

    override fun partOne(): Any {
        parse()
        for ((k, v) in tiles) {
            val list = mutableListOf<Rot>()
            list.add(Rot(v.first(), Direction.UP))
            list.add(Rot(v.first().reversed(), Direction.UP))
            list.add(Rot(v.last(), Direction.DOWN))
            list.add(Rot(v.last().reversed(), Direction.DOWN))
            var str1 = ""
            var str2 = ""
            for (i in 0 until tileSize) {
                str1 += v[i][0]
                str2 += v[i][tileSize - 1]
            }
            list.add(Rot(str1, Direction.LEFT))
            list.add(Rot(str1.reversed(), Direction.LEFT))
            list.add(Rot(str2, Direction.RIGHT))
            list.add(Rot(str2.reversed(), Direction.RIGHT))
            edges[k] = list
        }
        val keyList = tiles.keys.toList()
        for (i1 in keyList.indices) {
            val t1 = keyList[i1]
            val t1Edges = edges[t1]
            for (i2 in i1 + 1 until keyList.size) {
                val t2 = keyList[i2]
                val t2Edges = edges[t2]
                var didFit = false
                for (e1 in t1Edges!!) {
                    for (e2 in t2Edges!!) if (e1.edge == e2.edge) {
                        didFit = true
                        puzzle.add(Fit(t1, e1.direction, t2, e2.direction))
                        break
                    }
                    if (didFit) break
                }
                if (!didFit) continue
                tileFit[t1] = 1 + if (tileFit[t1] != null) tileFit[t1]!! else 0
                tileFit[t2] = 1 + if (tileFit[t2] != null) tileFit[t2]!! else 0
            }
        }
        var ret = 1L
        for ((k, v) in tileFit) {
            if (v == 2) {
                ret *= k
                if (start == -1) start = k
            }
        }
        return ret
    }

    override fun partTwo(): Any {
        val grid = constructGrid()
        val monsters = fetchMonsters()
        val count = mutableSetOf<Pair<Int, Int>>()
        var c2 = 0
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                count.addAll(fetchOverlap(x, y, grid, monsters))
            }
        }
        var total = grid.sumBy { it.count { it == '#' } }
        return total - count.size
    }

    var foundMonster = false
    fun fetchOverlap(x: Int, y: Int, grid: List<String>, monsters: MutableList<List<String>>): Set<Pair<Int, Int>> {
        val ret = mutableSetOf<Pair<Int, Int>>()
        for (monster in monsters) {
            if (monster.size + x > 95 || monster[0].length + y > 95) continue
            val amt = mutableSetOf<Pair<Int, Int>>()
            var valid = true
            for (i in monster.indices) {
                val ii = i + x
                for (j in monster[0].indices) {
                    if (monster[i][j] == ' ') continue
                    val jj = j + y
                    if (grid[ii][jj] != '#') {
                        valid = false; break
                    } else amt.add(Pair(ii, jj))
                }
                if (!valid) break
            }
            if (valid) {
                ret.addAll(amt)
                if(!foundMonster){
                    foundMonster = true
                    monsters.clear()
                    monsters.add(monster)
                }
                break
            }
        }
        return ret
    }

    fun constructGrid(): List<String> {
        var ret = mutableListOf<String>()
        val done = mutableSetOf<Int>()
        val tileGrid = Array(12) { IntArray(12) }
        val gridMap: MutableMap<Pair<Int, Int>, List<String>> = mutableMapOf()
        tileGrid[0][0] = start
        done.add(start)
        var connections = puzzle.filter { it.p1 == start || it.p2 == start }
            .map { if (it.p1 == start) Pair(it.dir1, it.p2) else Pair(it.dir2, it.p1) }

        var tile = tiles[start]!!
        var counter = 0
        while (connections.count { it.first == Direction.RIGHT || it.first == Direction.DOWN } != 2) {
            connections = connections.map { Pair(rotate(it.first, 1), it.second) }
            tile = rotateGrid(tile)
            if (counter++ > 4) {
                println("Error: A1")
                break
            }
        }

        gridMap[Pair(0, 0)] = tile

        val stack = mutableListOf<Pair<Int, Pair<Int, Int>>>()
        connections.forEach { stack.add(Pair(it.second, if (it.first == Direction.RIGHT) Pair(0, 1) else Pair(1, 0))) }

        while (stack.isNotEmpty()) {
            val current = stack.removeFirst()
            val id: Int = current.first
            if (done.contains(id)) continue
            val pos: Pair<Int, Int> = current.second
            val top: Int = if (pos.first == 0) -1 else tileGrid[pos.first - 1][pos.second]
            val left: Int = if (pos.second == 0) -1 else tileGrid[pos.first][pos.second - 1]
            val topTile = if (top == -1) null else gridMap[Pair(pos.first - 1, pos.second)]
            val leftTile = if (left == -1) null else gridMap[Pair(pos.first, pos.second - 1)]
            tile = tiles[id]!!
            tileGrid[pos.first][pos.second] = id
            connections = puzzle.filter { it.p1 == id || it.p2 == id }
                .map { if (it.p1 == id) Pair(it.dir1, it.p2) else Pair(it.dir2, it.p1) }

            var matched = false
            for (i in 1..4) {
                if (matches(top, left, connections)) {
                    matched = true; break
                }
                connections = connections.map { Pair(rotate(it.first, 1), it.second) }
                tile = rotateGrid(tile)
            }

            if (!matched) {
                tile = tile.map { it.reversed() }
                connections = connections.map {
                    when (it.first) {
                        Direction.RIGHT -> Pair(Direction.LEFT, it.second)
                        Direction.LEFT -> Pair(Direction.RIGHT, it.second)
                        else -> it
                    }
                }
                for (i in 1..4) {
                    if (matches(top, left, connections)) {
                        matched = true; break
                    }
                    connections = connections.map { Pair(rotate(it.first, 1), it.second) }
                    tile = rotateGrid(tile)
                }
                if (!matched) {
                    throw Exception("Couldn't fit Tile: $current")
                }
            }
            tile = flipTile(topTile, leftTile, tile, id, pos)

            gridMap[pos] = tile
            connections.filter { !done.contains(it.second) }.forEach {
                stack.add(
                    Pair(
                        it.second,
                        if (it.first == Direction.RIGHT) Pair(pos.first, pos.second + 1)
                        else Pair(pos.first + 1, pos.second)
                    )
                )
            }
            done.add(id)
        }

        for (i in 0..11) {
            var list = mutableListOf<String>()
            for (o in 0..7) list.add("")
            for (j in 0..11) {
                var gm = gridMap[Pair(i, j)]
                for (x in 0..7) {
                    var add = if (gm != null) gm[x+1].substring(1, 9)
                    else "XXXXXXXX"
                    list[x] = list[x] + add
                }
            }
            ret.addAll(list)
        }
        return ret
    }

    fun matches(top: Int, left: Int, con: List<Pair<Day20.Direction, Int>>): Boolean {
        var amt = 0
        if (top != -1) amt++
        if (left != -1) amt++
        return con.filter {
            (it.second == top && it.first == Direction.UP) ||
                    (it.second == left && it.first == Direction.LEFT)
        }.count() == amt
    }

    fun flipTile(
        top: List<String>?,
        left: List<String>?,
        tile: List<String>,
        id: Int,
        pos: Pair<Int, Int>
    ): List<String> {
        val flipV = tile.map { it.reversed() }
        val flipH = tile.reversed()
        val flipHV = flipV.reversed()
        val list = listOf(tile, flipV, flipH, flipHV)
        for (l in list) {
            if (top != null && top.last() != l.first()) continue
            if (left != null) {
                var invalid = false
                for (k in 0..9) {
                    if (left[k][9] != l[k][0]) {
                        invalid = true; break
                    }
                }
                if (invalid) continue
            }
            return l
        }
        println("Couldn't make it fit: $id, $pos")
        return tile
    }

    fun matchesTile(top: List<String>?, left: List<String>?, tile: List<String>): Boolean {
        if (top != null && tile.first() != top.last()) return false
        if (left != null) for (i in 0..10) if (left[9][i] != tile[0][i]) return false
        return true
    }

    fun doMatch(a: List<String>, b: List<String>, vertical: Boolean): Boolean {
        if (vertical) for (i in a.indices) {
            if (b[i][0] != a[i][a.size - 1]) return false
        } else return b.first() == a.last()
        return true
    }

    fun fetchMonsters(): MutableList<List<String>> {
        val ret = mutableListOf<List<String>>()
        var monster = fetchMonster()
        for (i in 0 until 4) {
            ret.add(monster)
            monster = rotateGrid(monster)
        }
        monster = monster.map { it.reversed() }
        for (i in 0 until 4) {
            ret.add(monster)
            monster = rotateGrid(monster)
        }
        monster = monster.reversed()
        for (i in 0 until 4) {
            ret.add(monster)
            monster = rotateGrid(monster)
        }
        monster = fetchMonster().reversed()
        for (i in 0 until 4) {
            ret.add(monster)
            monster = rotateGrid(monster)
        }
        return ret
    }

    fun fetchMonster(): List<String> {
        return listOf(
            "                  # ",
            "#    ##    ##    ###",
            " #  #  #  #  #  #   "
        )
    }

    fun rotate(dir: Direction, amt: Int): Direction {
        if (amt == 0) return dir
        return when (dir) {
            Direction.UP -> rotate(Direction.RIGHT, amt - 1)
            Direction.RIGHT -> rotate(Direction.DOWN, amt - 1)
            Direction.DOWN -> rotate(Direction.LEFT, amt - 1)
            Direction.LEFT -> rotate(Direction.UP, amt - 1)
        }
    }

    fun parse() {
        var tileId = -1
        var tile: MutableList<String> = mutableListOf()
        for (it in inputList) {
            if (it.isBlank()) {
                if (tileId != -1) tiles[tileId] = tile
                tile = mutableListOf()
                tileId = -1; continue
            } else if (it.startsWith("Tile ")) {
                tileId = it.split(" ")[1].replace(":", "").toInt()
            } else tile.add(it)
        }
        if (tileId != -1) tiles[tileId] = tile
    }

    fun rotateGrid(grid: List<String>): List<String> {
        val ret = mutableListOf<String>()
        for (i in grid[0].indices) ret.add("")
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                ret[j] = grid[i][j] + ret[j]
            }
        }
        return ret
    }

    fun printTest() {
        tiles.forEach { (k, v) ->
            println("Tile: $k")
            v.forEach { println(it) }
            println()
        }
        println(tiles.size)
    }
}
