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
        var count = 0
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                count += fetchOverlap(x, y, grid, monsters)
            }
        }
        return count
    }

    fun fetchOverlap(x: Int, y: Int, grid: List<String>, monsters: List<List<String>>): Int {
        var count = 0
        for (monster in monsters) {
            if (monster.size + x > grid.size || monster[0].length + y > grid[0].length) continue
            var amt = 0
            var valid = true
            for (i in monster.indices) {
                val ii = i + x
                for (j in monster[0].indices) {
                    val jj = j + y
                    if (monster[i][j] == '#' && grid[ii][jj] != '#') {
                        valid = false; break
                    }
                    if (grid[ii][jj] == '#') amt++
                }
                if (!valid) break
            }
            if (valid) count += amt - 15
        }
        return count
    }

    fun constructGrid(): List<String> {
        val ret = mutableListOf<String>()
        val done = mutableSetOf<Int>()
        var tileGrid = Array(12) { IntArray(12) }
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
                break;
            }
        }

        gridMap[Pair(0, 0)] = tile

        val stack = mutableListOf<Pair<Int, Pair<Int, Int>>>()
        connections.forEach { stack.add(Pair(it.second, if (it.first == Direction.RIGHT) Pair(0, 1) else Pair(1, 0))) }

        counter = 1
        while (stack.isNotEmpty()) {
            val current = stack.removeFirst()
//            println("done: ${done.joinToString()}")
//            println("Stack: $counter => $current")
//            stack.forEach { println(it) }
            val id: Int = current.first
            val pos: Pair<Int, Int> = current.second
            val top: Int = if (pos.first == 0) -1 else tileGrid[pos.first - 1][pos.second]
            val left: Int = if (pos.second == 0) -1 else tileGrid[pos.first][pos.second - 1]
            tile = tiles[id]!!
            tileGrid[pos.first][pos.second] = id
            var connections = puzzle.filter { it.p1 == id || it.p2 == id }
                .map { if (it.p1 == id) Pair(it.dir1, it.p2) else Pair(it.dir2, it.p1) }

//            if (id == 1871) {
//                println("top: $top, left: $left")
//                println("Tile:")
//                tile.forEach { println(it) }
//                println()
//                if (top != -1) {
//                    println("topTile: ")
//                    gridMap[Pair(pos.first - 1, pos.second)]!!.forEach { println(it) }
//                }
//                println()
//                if (top != -1) {
//                    println("leftTile: ")
//                    gridMap[Pair(pos.first, pos.second - 1)]!!.forEach { println(it) }
//                }
//            }

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
//                    if (doMatch(gridMap[prevPos]!!, tile, current.first.second)) {
                        matched = true; break
                    }
                    connections = connections.map { Pair(rotate(it.first, 1), it.second) }
                    tile = rotateGrid(tile)
                }
                if (!matched) {
                    throw Exception("Couldn't fit Tile: $current")
                }
            }
            gridMap[pos] = tile
            connections.filter {
                !done.contains(it.second) &&
                        ((pos.first == 0 && it.first == Direction.RIGHT) || it.first == Direction.RIGHT)
            }.forEach {
                stack.add(
                    Pair(
                        it.second,
                        if (it.first == Direction.RIGHT) Pair(pos.first, pos.second + 1)
                        else Pair(pos.first + 1, pos.second)
                    )
                )
            }
            done.add(id)

            if (counter++ > 145) {
                println("Error: A2")
                break;
            }

        }


        for (i in 0 until 12) {
            var list = mutableListOf<String>()
            for (o in 0 until 10) list.add("")
            for (j in 0 until 12) {
                var gm = gridMap[i]?.get(j)
                for (x in 0 until 10) {
                    var add = if (gm != null && gm[x] != null)  gm[x]
                    else "XXXXXXXXXX"
                    list[x] = list[x] + add
                }
            }
            ret.addAll(list)
        }
        println("Grid: ")
        ret.forEach { println(it) }

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

    fun doMatch(a: List<String>, b: List<String>, vertical: Boolean): Boolean {
        if (vertical) for (i in a.indices) {
            if (b[i][0] != a[i][a.size - 1]) return false
        } else return b.first() == a.last()
        return true
    }

    fun fetchMonsters(): List<List<String>> {
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
        when (dir) {
            Direction.UP -> return rotate(Direction.RIGHT, amt - 1)
            Direction.RIGHT -> return rotate(Direction.DOWN, amt - 1)
            Direction.DOWN -> return rotate(Direction.LEFT, amt - 1)
            Direction.LEFT -> return rotate(Direction.UP, amt - 1)
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
